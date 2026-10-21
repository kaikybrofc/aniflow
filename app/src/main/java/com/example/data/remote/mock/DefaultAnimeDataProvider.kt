package com.example.data.remote.mock

import com.example.domain.model.AnimeRelease
import com.example.domain.model.AnimeReleaseStatus
import com.example.domain.model.NewsArticle

object DefaultAnimeDataProvider {

    fun getDefaultNewsArticles(): List<NewsArticle> {
        val now = System.currentTimeMillis()
        val hour = 3600 * 1000L
        return listOf(
            NewsArticle(
                id = "news_1",
                title = "Frieren e a Jornada Para o Além: Segunda temporada ganha previsão oficial de estreia",
                author = "Redação AniFlow",
                source = "Crunchyroll News",
                publishedAtMillis = now - (hour * 2),
                category = "Anime",
                imageUrl = "https://images.unsplash.com/photo-1578632767115-351597cf2477?w=800",
                articleUrl = "https://myanimelist.net/anime/52991/Sousou_no_Frieren",
                summary = "O comitê de produção de Frieren: Beyond Journey's End revelou detalhes sobre o retorno da elfa maga e seus novos companheiros rumo ao norte de Ende."
            ),
            NewsArticle(
                id = "news_2",
                title = "Jujutsu Kaisen: Arco do Jogo do Abate (Culling Game) revela teaser inédito e visual de Maki Zen'in",
                author = "Lucas Tanaka",
                source = "Anime News Network",
                publishedAtMillis = now - (hour * 4),
                category = "Anime",
                imageUrl = "https://images.unsplash.com/photo-1607604276583-eef5d076aa5f?w=800",
                articleUrl = "https://myanimelist.net/news",
                summary = "O estúdio MAPPA divulgou o primeiro visual chave para a continuação de Jujutsu Kaisen, focando nas regras mortais estabelecidas por Kenjaku."
            ),
            NewsArticle(
                id = "news_3",
                title = "Chainsaw Man: Filme do Arco de Reze quebra recordes de bilheteria nas pré-estreias no Japão",
                author = "Beatriz Silva",
                source = "Comic Natalie",
                publishedAtMillis = now - (hour * 7),
                category = "Anime",
                imageUrl = "https://images.unsplash.com/photo-1563089145-599997674d42?w=800",
                articleUrl = "https://myanimelist.net/news",
                summary = "A adaptação cinematográfica do aguardado arco da Garota Bomba surpreendeu críticos e fãs com animação de altíssimo calibre e trilha sonora vibrante."
            ),
            NewsArticle(
                id = "news_4",
                title = "One Piece: Eiichiro Oda confirma pausa de 3 semanas no mangá para preparação do clímax de Elbaf",
                author = "Carlos Mendes",
                source = "Weekly Shonen Jump",
                publishedAtMillis = now - (hour * 12),
                category = "Mangá",
                imageUrl = "https://images.unsplash.com/photo-1534447677768-be436bb09401?w=800",
                articleUrl = "https://myanimelist.net/news",
                summary = "O autor declarou que o arco de Elbaf trará revelações históricas cruciais sobre os Século Perdido e o mistério de Shanks."
            ),
            NewsArticle(
                id = "news_5",
                title = "Dandadan: Adaptação da Science SARU ganha prêmio de melhor abertura de anime do ano",
                author = "Redação AniFlow",
                source = "Tokyo Anime Award",
                publishedAtMillis = now - (hour * 18),
                category = "Indústria",
                imageUrl = "https://images.unsplash.com/photo-1618005182384-a83a8bd57fbe?w=800",
                articleUrl = "https://myanimelist.net/news",
                summary = "A faixa 'Otonoke' da banda Creepy Nuts consolidou seu lugar no topo das paradas globais de streaming com visual psicodélico."
            ),
            NewsArticle(
                id = "news_6",
                title = "Solo Leveling Temporada 2: Episódio de estreia atinge pico de audiência mundial nos servidores",
                author = "Gabriel Rocha",
                source = "A-1 Pictures Info",
                publishedAtMillis = now - (hour * 24),
                category = "Anime",
                imageUrl = "https://images.unsplash.com/photo-1563245372-f21724e3856d?w=800",
                articleUrl = "https://myanimelist.net/news",
                summary = "Sung Jin-woo lidera o exército de sombras em sequências de batalha que superaram as expectativas dos leitores do webtoon coreano."
            ),
            NewsArticle(
                id = "news_7",
                title = "Anime Expo & Comic Con anunciam pavilhão temático com dubladores clássicos e estúdios japoneses",
                author = "Mariana Costa",
                source = "Otaku Events Global",
                publishedAtMillis = now - (hour * 36),
                category = "Eventos",
                imageUrl = "https://images.unsplash.com/photo-1511512578047-dfb367046420?w=800",
                articleUrl = "https://myanimelist.net/news",
                summary = "A próxima edição contará com estandes imersivos de Bleach, Demon Slayer e novidades sobre as próximas temporadas de verão."
            ),
            NewsArticle(
                id = "news_8",
                title = "Berserk: Estúdio 4°C e assistentes de Kentaro Miura anunciam novo projeto comemorativo",
                author = "Redação AniFlow",
                source = "Young Animal",
                publishedAtMillis = now - (hour * 48),
                category = "Mangá",
                imageUrl = "https://images.unsplash.com/photo-1579783902614-a3fb3927b675?w=800",
                articleUrl = "https://myanimelist.net/news",
                summary = "Exposição especial em Tóquio trará manuscritos originais e ilustrações inéditas mantidas em segredo pelos arquivos da editora."
            )
        )
    }

    fun getDefaultAnimeReleases(): List<AnimeRelease> {
        return listOf(
            // Segunda-feira (1)
            AnimeRelease(
                id = "sch_1",
                title = "Tower of God: 2nd Season",
                japaneseTitle = "Kami no Tou: Ouji no Kikan",
                imageUrl = "https://images.unsplash.com/photo-1578632767115-351597cf2477?w=800",
                dayOfWeek = 1,
                airingTime = "11:30",
                episodeNumber = 16,
                status = AnimeReleaseStatus.AIRING_TODAY,
                score = 7.8,
                genres = listOf("Ação", "Fantasia", "Mistério"),
                synopsis = "A jornada através da misteriosa torre continua com novas alianças e segredos sobre os governantes Zahard."
            ),
            AnimeRelease(
                id = "sch_2",
                title = "Bleach: Thousand-Year Blood War - The Conflict",
                japaneseTitle = "Bleach: Sennen Kessen-hen",
                imageUrl = "https://images.unsplash.com/photo-1607604276583-eef5d076aa5f?w=800",
                dayOfWeek = 1,
                airingTime = "13:00",
                episodeNumber = 32,
                status = AnimeReleaseStatus.UPCOMING,
                score = 9.1,
                genres = listOf("Ação", "Sobrenatural", "Shounen"),
                synopsis = "Ichigo Kurosaki e o Gotei 13 enfrentam a Guarda Real de Yhwach no palácio do Rei das Almas."
            ),

            // Terça-feira (2)
            AnimeRelease(
                id = "sch_3",
                title = "Dandadan",
                japaneseTitle = "Dandadan",
                imageUrl = "https://images.unsplash.com/photo-1618005182384-a83a8bd57fbe?w=800",
                dayOfWeek = 2,
                airingTime = "12:00",
                episodeNumber = 8,
                status = AnimeReleaseStatus.AIRING_TODAY,
                score = 8.7,
                genres = listOf("Comédia", "Sobrenatural", "Sci-Fi"),
                synopsis = "Momo Ayase e Okarun enfrentam fantasmas e alienígenas em um confronto hilário e frenético."
            ),
            AnimeRelease(
                id = "sch_4",
                title = "Blue Lock vs. U-20 Japan",
                japaneseTitle = "Blue Lock 2nd Season",
                imageUrl = "https://images.unsplash.com/photo-1563245372-f21724e3856d?w=800",
                dayOfWeek = 2,
                airingTime = "14:30",
                episodeNumber = 7,
                status = AnimeReleaseStatus.UPCOMING,
                score = 8.2,
                genres = listOf("Esportes", "Competição", "Shounen"),
                synopsis = "Isagi Yoichi lidera a equipe Blue Lock no jogo definitivo contra a seleção sub-20 japonesa."
            ),

            // Quarta-feira (3)
            AnimeRelease(
                id = "sch_5",
                title = "Re:Zero - Starting Life in Another World 3rd Season",
                japaneseTitle = "Re:Zero kara Hajimeru Isekai Seikatsu",
                imageUrl = "https://images.unsplash.com/photo-1563089145-599997674d42?w=800",
                dayOfWeek = 3,
                airingTime = "10:30",
                episodeNumber = 9,
                status = AnimeReleaseStatus.AIRING_TODAY,
                score = 8.9,
                genres = listOf("Drama", "Fantasia", "Suspense"),
                synopsis = "Subaru Natsuki enfrenta os Arcebispos do Pecado na cidade aquática de Priestella."
            ),
            AnimeRelease(
                id = "sch_6",
                title = "Shangri-La Frontier 2nd Season",
                japaneseTitle = "Shangri-La Frontier",
                imageUrl = "https://images.unsplash.com/photo-1511512578047-dfb367046420?w=800",
                dayOfWeek = 3,
                airingTime = "15:00",
                episodeNumber = 6,
                status = AnimeReleaseStatus.UPCOMING,
                score = 8.1,
                genres = listOf("Ação", "Aventura", "Games"),
                synopsis = "Sunraku explora os maiores mistérios dos Monstros Únicos no mundo de ShanFro."
            ),

            // Quinta-feira (4)
            AnimeRelease(
                id = "sch_7",
                title = "Dr. STONE: Science Future",
                japaneseTitle = "Dr. Stone 4th Season",
                imageUrl = "https://images.unsplash.com/photo-1579783902614-a3fb3927b675?w=800",
                dayOfWeek = 4,
                airingTime = "11:00",
                episodeNumber = 12,
                status = AnimeReleaseStatus.AIRING_TODAY,
                score = 8.4,
                genres = listOf("Aventura", "Sci-Fi", "Comédia"),
                synopsis = "Senku e o Reino da Ciência rumam à América em busca do milho e das peças para a nave espacial."
            ),
            AnimeRelease(
                id = "sch_8",
                title = "Trigun Stargaze",
                japaneseTitle = "Trigun Stargaze",
                imageUrl = "https://images.unsplash.com/photo-1534447677768-be436bb09401?w=800",
                dayOfWeek = 4,
                airingTime = "16:30",
                episodeNumber = 4,
                status = AnimeReleaseStatus.UPCOMING,
                score = 8.3,
                genres = listOf("Ação", "Sci-Fi", "Pós-Apocalíptico"),
                synopsis = "O desfecho do embate entre Vash the Stampede e seu irmão Knives na Terra de Ninguém."
            ),

            // Sexta-feira (5)
            AnimeRelease(
                id = "sch_9",
                title = "Frieren: Beyond Journey's End (Especial)",
                japaneseTitle = "Sousou no Frieren",
                imageUrl = "https://images.unsplash.com/photo-1578632767115-351597cf2477?w=800",
                dayOfWeek = 5,
                airingTime = "12:00",
                episodeNumber = 29,
                status = AnimeReleaseStatus.AIRING_TODAY,
                score = 9.3,
                genres = listOf("Aventura", "Drama", "Fantasia"),
                synopsis = "Episódio especial retrospectivo e preparação para a viagem rumo ao extremo norte."
            ),
            AnimeRelease(
                id = "sch_10",
                title = "Ranma ½ (2024)",
                japaneseTitle = "Ranma Nibun-no-Ichi",
                imageUrl = "https://images.unsplash.com/photo-1563245372-f21724e3856d?w=800",
                dayOfWeek = 5,
                airingTime = "14:00",
                episodeNumber = 10,
                status = AnimeReleaseStatus.UPCOMING,
                score = 8.0,
                genres = listOf("Comédia", "Artes Marciais", "Romance"),
                synopsis = "A nova animação do estúdio MAPPA recria com maestria as confusões de Ranma Saotome e Akane Tendo."
            ),

            // Sábado (6)
            AnimeRelease(
                id = "sch_11",
                title = "Solo Leveling Season 2: Arise from the Shadow",
                japaneseTitle = "Na Honjaman Rebeleop",
                imageUrl = "https://images.unsplash.com/photo-1563089145-599997674d42?w=800",
                dayOfWeek = 6,
                airingTime = "13:30",
                episodeNumber = 18,
                status = AnimeReleaseStatus.AIRING_TODAY,
                score = 8.8,
                genres = listOf("Ação", "Fantasia"),
                synopsis = "O monarca das sombras desperta forças ancestrais para salvar a Ilha de Jeju."
            ),
            AnimeRelease(
                id = "sch_12",
                title = "My Hero Academia: Final Season Preludes",
                japaneseTitle = "Boku no Hero Academia Season 8",
                imageUrl = "https://images.unsplash.com/photo-1607604276583-eef5d076aa5f?w=800",
                dayOfWeek = 6,
                airingTime = "17:30",
                episodeNumber = 1,
                status = AnimeReleaseStatus.UPCOMING,
                score = 8.5,
                genres = listOf("Ação", "Super-herói", "Shounen"),
                synopsis = "Deku e a turma 1-A realizam o último treinamento conjunto antes da batalha contra Shigaraki e All For One."
            ),

            // Domingo (7)
            AnimeRelease(
                id = "sch_13",
                title = "One Piece: Arco Elbaf",
                japaneseTitle = "One Piece",
                imageUrl = "https://images.unsplash.com/photo-1534447677768-be436bb09401?w=800",
                dayOfWeek = 7,
                airingTime = "09:30",
                episodeNumber = 1125,
                status = AnimeReleaseStatus.AIRING_TODAY,
                score = 8.9,
                genres = listOf("Ação", "Aventura", "Fantasia"),
                synopsis = "Luffy e o Bando do Chapéu de Palha finalmente chegam à lendária ilha dos gigantes, Elbaf."
            ),
            AnimeRelease(
                id = "sch_14",
                title = "Demon Slayer: Infinity Castle - Movie Arc Info",
                japaneseTitle = "Kimetsu no Yaiba: Mugen Jou-hen",
                imageUrl = "https://images.unsplash.com/photo-1579783902614-a3fb3927b675?w=800",
                dayOfWeek = 7,
                airingTime = "18:00",
                episodeNumber = 1,
                status = AnimeReleaseStatus.UPCOMING,
                score = 9.2,
                genres = listOf("Ação", "Histórico", "Sobrenatural"),
                synopsis = "Tanjiro, Nezuko e os Hashiras invadem a fortaleza dimensional de Muzan Kibutsuji."
            )
        )
    }
}
