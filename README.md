# spotify-oauth-server

A Spotify oauth flow server

## Usage

First setup your profiles.clj as follows

```clj
{:dev {:env {
             :redirect-uri ""
             :client-secret ""
             :client-id ""}}}
```

Then set your client-id in `web/public/index.html`


Run the server with

`lein ring server`

## License

MIT

