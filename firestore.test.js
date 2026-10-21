const test = require('node:test');
const assert = require('node:assert/strict');
const fs = require('node:fs');
const {
  initializeTestEnvironment,
  assertFails,
  assertSucceeds,
} = require('@firebase/rules-unit-testing');

const PROJECT_ID = 'demo-no-project';
const DATABASE_ID = 'ai-studio-android-aniflow-9d37d47c-daac-4826-b34e-9c194e994e53';

let testEnv;

test.before(async () => {
  testEnv = await initializeTestEnvironment({
    projectId: PROJECT_ID,
    firestore: {
      rules: fs.readFileSync('firestore.rules', 'utf8'),
      host: '127.0.0.1',
      port: 8085,
    },
  });
});

test.after(async () => {
  if (testEnv) {
    await testEnv.cleanup();
  }
});

test.afterEach(async () => {
  if (testEnv) {
    await testEnv.clearFirestore();
  }
});

test('Unauthenticated user cannot write profile', async () => {
  const unauthedDb = testEnv.unauthenticatedContext().firestore();
  await assertFails(
    unauthedDb.doc('users/alice').set({
      userId: 'alice',
      displayName: 'Alice Otaku',
      updatedAt: new Date(),
    })
  );
});

test('Authenticated user can write and read own profile', async () => {
  const aliceDb = testEnv.authenticatedContext('alice').firestore();
  await assertSucceeds(
    aliceDb.doc('users/alice').set({
      userId: 'alice',
      displayName: 'Alice Otaku',
      updatedAt: new Date(),
    })
  );
  await assertSucceeds(aliceDb.doc('users/alice').get());
});

test('User cannot read or write another user profile', async () => {
  const aliceDb = testEnv.authenticatedContext('alice').firestore();
  await assertFails(
    aliceDb.doc('users/bob').set({
      userId: 'bob',
      displayName: 'Bob Otaku',
      updatedAt: new Date(),
    })
  );
  await assertFails(aliceDb.doc('users/bob').get());
});

test('User can save and read own bookmarks', async () => {
  const aliceDb = testEnv.authenticatedContext('alice').firestore();
  await assertSucceeds(
    aliceDb.doc('users/alice/bookmarks/art_1').set({
      id: 'art_1',
      userId: 'alice',
      title: 'Frieren Season 2 Anunciada',
      savedAt: new Date(),
    })
  );
  await assertSucceeds(aliceDb.doc('users/alice/bookmarks/art_1').get());
});

test('User cannot save bookmarks in another user path', async () => {
  const aliceDb = testEnv.authenticatedContext('alice').firestore();
  await assertFails(
    aliceDb.doc('users/bob/bookmarks/art_1').set({
      id: 'art_1',
      userId: 'bob',
      title: 'Frieren Season 2 Anunciada',
      savedAt: new Date(),
    })
  );
});
