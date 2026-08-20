# Spotify API

API REST desenvolvida em Java e Spring Boot para integração com a API do Spotify.

O projeto permite consultar artistas no Spotify, armazenar seus dados e álbuns em banco de dados PostgreSQL e gerar um ranking de artistas baseado em popularidade.

## Tecnologias

- Java
- Spring Boot
- Spring Data JPA
- OpenFeign
- PostgreSQL
- Maven
- Lombok
- JUnit
- Mockito
- Spotify Web API
- OAuth 2.0

## Funcionalidades

- Autenticação na API do Spotify utilizando OAuth 2.0 Client Credentials
- Controle e reutilização do Access Token enquanto estiver válido
- Consulta de artistas através da Spotify Web API
- Persistência de artistas no PostgreSQL
- Consulta e persistência de álbuns
- Prevenção de artistas e álbuns duplicados
- Busca de artistas cadastrados
- Busca de álbuns por artista
- Ranking de artistas por popularidade
- Tratamento global de exceções
- Testes unitários com JUnit e Mockito

## Endpoints

### Cadastrar artista

```http
POST /artist/{spotifyId}
```

Consulta o artista no Spotify e salva seus dados no banco.

### Cadastrar álbuns do artista

```http
POST /artist/{spotifyId}/albums
```

Consulta os álbuns do artista no Spotify e salva no banco.

### Buscar artista por ID

```http
GET /artist/{id}
```

### Buscar todos os artistas

```http
GET /artist
```

### Buscar álbuns de um artista

```http
GET /artist/{artistId}/albums
```

### Ranking de artistas

```http
GET /artist/ranking
```

Retorna os artistas ordenados pela popularidade.

## Autenticação com Spotify

A aplicação utiliza o fluxo OAuth 2.0 Client Credentials para obter um Access Token.

As credenciais não ficam armazenadas diretamente no código. Elas são obtidas através das variáveis de ambiente:

```text
SPOTIFY_CLIENT_ID
SPOTIFY_CLIENT_SECRET
```

No `application.properties`:

```properties
spotify.client-id=${SPOTIFY_CLIENT_ID}
spotify.client-secret=${SPOTIFY_CLIENT_SECRET}
```

## Banco de dados

O projeto utiliza PostgreSQL para persistência dos dados.

Principais entidades:

```text
Artist
  |
  └── Albums
```

Um artista pode possuir vários álbuns.

## Tratamento de erros

A API possui tratamento global de exceções através de `@RestControllerAdvice`.

Entre os erros tratados estão:

- artista não encontrado;
- artista já cadastrado;
- erro de comunicação com a Spotify Web API.

Os erros são retornados em uma estrutura padronizada contendo informações como status HTTP, mensagem, horário e endpoint.

## Testes

O projeto possui testes para as principais camadas e regras da aplicação utilizando JUnit e Mockito.

Entre os componentes testados estão:

- `ArtistService`
- `SpotifyAuthService`
- `ArtistController`
- `ArtistMapper`
- `AlbumMapper`

Também existe um teste de carregamento do contexto do Spring Boot.

## Como executar

Clone o projeto:

```bash
git clone URL_DO_SEU_REPOSITORIO
```

Configure as variáveis de ambiente:

```text
SPOTIFY_CLIENT_ID
SPOTIFY_CLIENT_SECRET
```

Configure o PostgreSQL conforme as propriedades da aplicação e execute o projeto pela IDE ou utilizando Maven:

```bash
./mvnw spring-boot:run
```

## Objetivo

Projeto desenvolvido para praticar desenvolvimento backend com Java e Spring Boot, integração com APIs externas, OAuth 2.0, OpenFeign, persistência com JPA, tratamento de exceções e testes unitários.