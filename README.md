# Spotify API

API REST desenvolvida em Java e Spring Boot para integração com a Spotify Web API.

O projeto permite consultar artistas e músicas na Spotify Web API, armazenar artistas e álbuns em PostgreSQL e criar playlists personalizadas com músicas obtidas diretamente do Spotify.

## Tecnologias

- Java 17
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
- Swagger / OpenAPI

## Funcionalidades

- Autenticação na API do Spotify utilizando OAuth 2.0 Client Credentials
- Controle e reutilização do Access Token enquanto estiver válido
- Consulta de artistas através da Spotify Web API
- Persistência de artistas no PostgreSQL
- Consulta e persistência de álbuns
- Prevenção de artistas e álbuns duplicados
- Busca de artistas cadastrados
- Busca de álbuns por artista
- Paginação e ordenação de artistas
- Criação de playlists
- Busca de playlist por nome
- Busca de playlist sem diferenciar letras maiúsculas e minúsculas
- Listagem de playlists ordenadas pela data de criação
- Consulta de músicas através da Spotify Web API
- Adição de músicas às playlists
- Prevenção de músicas duplicadas dentro da mesma playlist
- Tratamento de erros de comunicação com a Spotify Web API
- Tratamento global de exceções
- Respostas de erro padronizadas
- Testes unitários com JUnit e Mockito
- Documentação interativa com Swagger/OpenAPI

## Endpoints

### Artistas

#### Cadastrar artista

```http
POST /artists/{spotifyId}
```

Consulta o artista na API do Spotify e salva seus dados no banco.

#### Cadastrar álbuns do artista

```http
POST /artists/{spotifyId}/albums
```

Consulta os álbuns do artista no Spotify e salva os que ainda não estão cadastrados.

#### Buscar artista por ID

```http
GET /artists/{id}
```

Retorna um artista pelo ID interno do banco de dados.

#### Buscar todos os artistas

```http
GET /artists?page=0&size=5&direction=asc
```

Retorna os artistas cadastrados utilizando paginação.

O parâmetro `direction` permite definir a ordenação por nome:

```text
asc
desc
```

#### Buscar álbuns de um artista

```http
GET /artists/{artistId}/albums
```

Retorna os álbuns cadastrados de determinado artista.

---

### Playlists

#### Criar playlist

```http
POST /playlist
```

Cria uma nova playlist e armazena seus dados no PostgreSQL.

Exemplo de requisição:

```json
{
  "name": "Treino Pesado",
  "description": "Playlist para academia"
}
```

#### Buscar playlist por nome

```http
GET /playlist/{name}
```

Retorna uma playlist cadastrada através do nome.

A busca não diferencia letras maiúsculas de minúsculas.

Por exemplo:

```text
Treino Pesado
treino pesado
TREINO PESADO
```

podem localizar a mesma playlist.

#### Buscar todas as playlists

```http
GET /playlist
```

Retorna todas as playlists cadastradas, ordenadas pela data de criação em ordem crescente.

#### Adicionar música à playlist

```http
POST /playlist/{playlistId}/tracks/{spotifyTrackId}
```

Consulta a música através da Spotify Web API e adiciona a música à playlist informada.

Exemplo:

```http
POST /playlist/1/tracks/2nLtzopw4rPReszdYBJU6h
```

A aplicação impede que a mesma música seja cadastrada mais de uma vez dentro da mesma playlist.

## Integração com Spotify

A aplicação utiliza OpenFeign para realizar a comunicação com a Spotify Web API.

Entre os recursos externos consultados estão:

```text
Artistas
Álbuns
Músicas
```

O fluxo básico de integração é:

```text
Cliente
   ↓
Controller
   ↓
Service
   ↓
SpotifyAuthService
   ↓
Access Token
   ↓
SpotifyClient
   ↓
Spotify Web API
```

Após receber os dados do Spotify, a aplicação realiza o mapeamento para as entidades internas antes da persistência.

## Autenticação com Spotify

A aplicação utiliza o fluxo OAuth 2.0 Client Credentials para obter um Access Token da Spotify Web API.

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

O Access Token é reutilizado enquanto estiver válido, evitando solicitações desnecessárias de novos tokens.

## Banco de dados

O projeto utiliza PostgreSQL para persistência dos dados.

As principais relações da aplicação são:

```text
Artist
   |
   └── Albums


Playlist
   |
   └── Tracks
```

Um artista pode possuir vários álbuns.

Uma playlist pode possuir várias músicas.

## Arquitetura

A aplicação segue uma separação em camadas:

```text
Controller
    ↓
Service
    ↓
Repository
    ↓
PostgreSQL
```

Nas operações que dependem do Spotify:

```text
Controller
    ↓
Service
    ├── Repository
    │       ↓
    │   PostgreSQL
    │
    └── SpotifyClient
            ↓
       Spotify Web API
```

Os mappers são responsáveis pela conversão entre os objetos recebidos da API externa, entidades da aplicação e DTOs de resposta.

## Tratamento de erros

A API possui tratamento global de exceções através de `@RestControllerAdvice`.

Entre os erros tratados estão:

- artista não encontrado;
- artista já cadastrado;
- playlist não encontrada;
- playlist já cadastrada;
- nome de playlist inválido;
- descrição de playlist inválida;
- música já cadastrada na playlist;
- direção de ordenação inválida;
- erro de comunicação com a Spotify Web API.

Os erros seguem uma estrutura padronizada através do `StandardError`.

Exemplo:

```json
{
  "timestamp": "2026-08-26T12:00:00Z",
  "status": 404,
  "error": "Not Found",
  "message": "Playlist não encontrada",
  "path": "/playlist/Inexistente"
}
```

## Swagger / OpenAPI

A API possui documentação interativa utilizando Swagger e OpenAPI.

Com a aplicação em execução, a documentação pode ser acessada em:

```text
http://localhost:8080/swagger-ui.html
```

A especificação OpenAPI também está disponível em:

```text
http://localhost:8080/v3/api-docs
```

Pelo Swagger é possível visualizar os endpoints, parâmetros, modelos de resposta, possíveis erros e executar requisições diretamente pela interface.

## Testes

O projeto possui testes utilizando JUnit e Mockito para validar as principais regras e componentes da aplicação.

Entre os componentes testados estão:

```text
ArtistService
PlaylistService
SpotifyAuthService

ArtistController
PlaylistController

ArtistMapper
AlbumMapper
PlaylistMapper
TrackMapper
```

Também existe um teste responsável por validar o carregamento do contexto da aplicação Spring Boot.

Os testes cobrem cenários como:

- cadastro e consulta de artistas;
- validação de artistas duplicados;
- paginação e ordenação;
- criação de playlists;
- validações de playlist;
- busca de playlist por nome;
- listagem de playlists;
- adição de músicas;
- prevenção de músicas duplicadas;
- falhas na comunicação com o Spotify;
- conversão entre entidades e DTOs.

## Estrutura do projeto

```text
com.br.spotifyapi
├── client
│   └── dto
├── config
├── controller
├── exceptions
├── models
│   ├── dto
│   ├── entites
│   └── mapper
├── repositories
├── service
└── SpotifyApiApplication
```

A aplicação mantém separadas as responsabilidades de entrada HTTP, regras de negócio, persistência, mapeamento de objetos e comunicação com serviços externos.

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

Configure o PostgreSQL conforme as propriedades da aplicação.

Execute pela IDE ou utilizando Maven:

```bash
./mvnw spring-boot:run
```

Com a aplicação iniciada, a API estará disponível em:

```text
http://localhost:8080
```

E a documentação Swagger em:

```text
http://localhost:8080/swagger-ui.html
```

## Objetivo

Projeto desenvolvido para praticar e consolidar conceitos de desenvolvimento backend com Java e Spring Boot, incluindo arquitetura em camadas, integração com APIs externas, OAuth 2.0, OpenFeign, persistência com JPA, relacionamentos entre entidades, PostgreSQL, DTOs, mappers, paginação, tratamento de exceções, testes unitários e documentação de APIs com Swagger/OpenAPI.