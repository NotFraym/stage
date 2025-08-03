CREATE TABLE resultat (
                          id SERIAL PRIMARY KEY,
                          reponse TEXT NOT NULL,
                          evaluation TEXT,
                          temps_ms INTEGER NOT NULL,
                          executed_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                          prompt TEXT NOT NULL,
                          llm VARCHAR NOT NULL,
                          question TEXT NOT NULL
);
