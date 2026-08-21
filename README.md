# Spotify API

API REST desenvolvida em Java e Spring Boot para integração com a Spotify Web API.

O projeto permite consultar artistas na Spotify Web API, armazenar seus dados e álbuns em PostgreSQL e disponibilizar endpoints para consulta e gerenciamento dessas informações

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
- Ranking de artistas por popularidade
- Tratamento global de exceções
- Respostas de erro padronizadas
- Testes unitários com JUnit e Mockito
- Documentação interativa com Swagger/OpenAPI

## Endpoints

### Cadastrar artista

```http
POST /artists/{spotifyId}
```

Consulta o artista na API do Spotify e salva seus dados no banco.

### Cadastrar álbuns do artista

```http
POST /artists/{spotifyId}/albums
```

Consulta os álbuns do artista no Spotify e salva os que ainda não estão cadastrados.

### Buscar artista por ID

```http
GET /artists/{id}
```

Retorna um artista pelo ID interno do banco de dados.

### Buscar todos os artistas

```http
GET /artists
```

Retorna todos os artistas cadastrados.

### Buscar álbuns de um artista

```http
GET /artists/{artistId}/albums
```

Retorna os álbuns cadastrados de determinado artista.

### Ranking de artistas

```http
GET /artists/ranking
```

Retorna os artistas ordenados por popularidade, do maior para o menor.

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

As principais entidades são:

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

Os erros seguem uma estrutura padronizada através do `StandardError`:

```json
{
  "timestamp": "2026-08-21T12:00:00Z",
  "status": 404,
  "error": "Not Found",
  "message": "Artista não encontrado",
  "path": "/artists/999"
}
```

## Testes

O projeto possui testes para as principais camadas e regras da aplicação utilizando JUnit e Mockito.

Entre os componentes testados estão:

- `ArtistService`
- `SpotifyAuthService`
- `ArtistController`
- `ArtistMapper`
- `AlbumMapper`

Também existe um teste de carregamento do contexto do Spring Boot.

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
│   ├── entities
│   └── mapper
├── repositories
├── service
└── SpotifyApiApplication
```

A aplicação segue uma separação em camadas, mantendo responsabilidades de controller, regras de negócio, persistência, mapeamento e comunicação com serviços externos.

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

Projeto desenvolvido para praticar e consolidar conceitos de desenvolvimento backend com Java e Spring Boot, incluindo integração com APIs externas, OAuth 2.0, OpenFeign, persistência com JPA, PostgreSQL, tratamento de exceções, testes unitários e documentação de APIs com Swagger/OpenAPI.