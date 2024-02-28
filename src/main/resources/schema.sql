-- books table
create table if not exists books (
    book_id serial primary key,
    book_name character varying(255),
    book_writer character varying(255)
);
