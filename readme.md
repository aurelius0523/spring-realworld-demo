## spring-realworld-demo

A simple demo of
implementing [realworld](https://github.com/gothinkster/realworld) using Spring.
It follows a standard layered architecture or _package by layer_.

## To use API

1. Import `Conduit.postman_collection.json`
2. Start by calling `/login` api
3. Interact with other endpoints

## Commentary

### Data

This project serves a learning project so you'll also see `QueryDsl`,
spring-data's Query and Method being used. Mapper like `mapstruct` which can
make mapping for empty fields during update operation is ommitted for
simplicity.

### Architecture

`package by layer` is not the most maintainable package strategy as it'll turn
the project into a scrolling simulator as it grow.