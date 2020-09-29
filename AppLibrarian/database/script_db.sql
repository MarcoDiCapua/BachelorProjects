--
-- PostgreSQL database dump
--

-- Dumped from database version 9.5.10
-- Dumped by pg_dump version 9.5.10

-- Started on 2018-01-30 01:14:37

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 2171 (class 1262 OID 16393)
-- Name: libraryDb; Type: DATABASE; Schema: -; Owner: postgres
--

CREATE DATABASE "libraryDb" WITH TEMPLATE = template0 ENCODING = 'UTF8' LC_COLLATE = 'Italian_Italy.1252' LC_CTYPE = 'Italian_Italy.1252';


ALTER DATABASE "libraryDb" OWNER TO postgres;

\connect "libraryDb"

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 1 (class 3079 OID 12355)
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- TOC entry 2174 (class 0 OID 0)
-- Dependencies: 1
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

--
-- TOC entry 591 (class 1247 OID 51517)
-- Name: activation_counter; Type: DOMAIN; Schema: public; Owner: postgres
--

CREATE DOMAIN activation_counter AS integer
	CONSTRAINT counter_check CHECK (((VALUE > '-1'::integer) AND (VALUE < 6)));


ALTER DOMAIN activation_counter OWNER TO postgres;

--
-- TOC entry 587 (class 1247 OID 51513)
-- Name: category; Type: DOMAIN; Schema: public; Owner: postgres
--

CREATE DOMAIN category AS text NOT NULL
	CONSTRAINT category_check CHECK ((VALUE = ANY (ARRAY['BIOLOGY'::text, 'CHEMISTRY'::text, 'RIGHTS'::text, 'ECONOMIC'::text, 'PHILOSOPHY'::text, 'COMPUTER_SCIENCE'::text, 'ENGINEERING'::text, 'LANGUAGES'::text, 'MATHEMATICS'::text, 'MEDICINE'::text, 'PSYCHOLOGY'::text])));


ALTER DOMAIN category OWNER TO postgres;

--
-- TOC entry 585 (class 1247 OID 51510)
-- Name: classification; Type: DOMAIN; Schema: public; Owner: postgres
--

CREATE DOMAIN classification AS character varying(20) NOT NULL
	CONSTRAINT classification_check CHECK (((VALUE)::text = ANY ((ARRAY['LIBRARIAN'::character varying, 'ADMINISTRATOR'::character varying, 'TECHNICIAN'::character varying, 'TEACHER'::character varying, 'STUDENT'::character varying])::text[])));


ALTER DOMAIN classification OWNER TO postgres;

--
-- TOC entry 589 (class 1247 OID 51515)
-- Name: language; Type: DOMAIN; Schema: public; Owner: postgres
--

CREATE DOMAIN language AS text NOT NULL
	CONSTRAINT language_check CHECK ((VALUE = ANY (ARRAY['ITALIAN'::text, 'FRENCH'::text, 'GERMAN'::text, 'SPANISH'::text, 'CHINESE'::text])));


ALTER DOMAIN language OWNER TO postgres;

--
-- TOC entry 181 (class 1259 OID 51841)
-- Name: authors_id_aut_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE authors_id_aut_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE authors_id_aut_seq OWNER TO postgres;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 182 (class 1259 OID 51843)
-- Name: authors; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE authors (
    name character varying(15) NOT NULL,
    surname character varying(20) NOT NULL,
    id_aut integer DEFAULT nextval('authors_id_aut_seq'::regclass) NOT NULL
);


ALTER TABLE authors OWNER TO postgres;

--
-- TOC entry 188 (class 1259 OID 51898)
-- Name: bookings_id_booking_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE bookings_id_booking_seq
    START WITH 34
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE bookings_id_booking_seq OWNER TO postgres;

--
-- TOC entry 189 (class 1259 OID 51900)
-- Name: bookings; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE bookings (
    id_user character(16) NOT NULL,
    id_book character(13) NOT NULL,
    booking_date date NOT NULL,
    id_booking integer DEFAULT nextval('bookings_id_booking_seq'::regclass) NOT NULL
);


ALTER TABLE bookings OWNER TO postgres;

--
-- TOC entry 183 (class 1259 OID 51849)
-- Name: books; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE books (
    isbn character(13) NOT NULL,
    title text NOT NULL,
    bookcase character varying(5),
    category category,
    publishing_house text NOT NULL,
    publication_year character(4) NOT NULL,
    reprint_year character(4),
    language language,
    CONSTRAINT check_year CHECK ((reprint_year >= publication_year))
);


ALTER TABLE books OWNER TO postgres;

--
-- TOC entry 186 (class 1259 OID 51879)
-- Name: loans_id_loan_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE loans_id_loan_seq
    START WITH 86
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE loans_id_loan_seq OWNER TO postgres;

--
-- TOC entry 187 (class 1259 OID 51881)
-- Name: loans; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE loans (
    id_book character(13),
    id_user character(16),
    loan_date date NOT NULL,
    return_date date NOT NULL,
    returned boolean DEFAULT false NOT NULL,
    id_loan integer DEFAULT nextval('loans_id_loan_seq'::regclass) NOT NULL,
    CONSTRAINT check_date CHECK ((return_date >= loan_date))
);


ALTER TABLE loans OWNER TO postgres;

--
-- TOC entry 184 (class 1259 OID 51857)
-- Name: users; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE users (
    name character varying(15) NOT NULL,
    surname character varying(25) NOT NULL,
    fiscal_code character(16) NOT NULL,
    telephone_number character varying(10) NOT NULL,
    email text NOT NULL,
    classification classification,
    year_class character varying(5),
    activation_code character(8),
    password character(32) NOT NULL,
    temporaney_pwd boolean DEFAULT false NOT NULL,
    counter activation_counter
);


ALTER TABLE users OWNER TO postgres;

--
-- TOC entry 191 (class 1259 OID 51963)
-- Name: view_librarian; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW view_librarian AS
 SELECT users.name,
    users.surname,
    users.fiscal_code,
    users.telephone_number,
    users.email,
    users.classification,
    users.year_class,
    users.activation_code,
    users.password,
    users.temporaney_pwd,
    users.counter
   FROM users
  WHERE ((users.classification)::text = 'LIBRARIAN'::text);


ALTER TABLE view_librarian OWNER TO postgres;

--
-- TOC entry 185 (class 1259 OID 51866)
-- Name: write; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE write (
    id_book character(13) NOT NULL,
    id_author integer,
    id_write integer NOT NULL
);


ALTER TABLE write OWNER TO postgres;

--
-- TOC entry 190 (class 1259 OID 51941)
-- Name: write_id_write_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE write_id_write_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE write_id_write_seq OWNER TO postgres;

--
-- TOC entry 2175 (class 0 OID 0)
-- Dependencies: 190
-- Name: write_id_write_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE write_id_write_seq OWNED BY write.id_write;


--
-- TOC entry 2024 (class 2604 OID 51943)
-- Name: id_write; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY write ALTER COLUMN id_write SET DEFAULT nextval('write_id_write_seq'::regclass);


--
-- TOC entry 2034 (class 2606 OID 51865)
-- Name: FiscalCodePk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY users
    ADD CONSTRAINT "FiscalCodePk" PRIMARY KEY (fiscal_code);


--
-- TOC entry 2032 (class 2606 OID 51856)
-- Name: IsbnPk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY books
    ADD CONSTRAINT "IsbnPk" PRIMARY KEY (isbn);


--
-- TOC entry 2030 (class 2606 OID 51848)
-- Name: authors_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY authors
    ADD CONSTRAINT authors_pk PRIMARY KEY (id_aut);


--
-- TOC entry 2044 (class 2606 OID 51905)
-- Name: bookings_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY bookings
    ADD CONSTRAINT bookings_pk PRIMARY KEY (id_user, id_book);


--
-- TOC entry 2042 (class 2606 OID 51887)
-- Name: loans_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY loans
    ADD CONSTRAINT loans_pk PRIMARY KEY (id_loan);


--
-- TOC entry 2038 (class 2606 OID 51960)
-- Name: write_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY write
    ADD CONSTRAINT write_pk PRIMARY KEY (id_write);


--
-- TOC entry 2035 (class 1259 OID 51952)
-- Name: fki_authors_fk; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX fki_authors_fk ON write USING btree (id_author);


--
-- TOC entry 2045 (class 1259 OID 51922)
-- Name: fki_book_isbn_fk; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX fki_book_isbn_fk ON bookings USING btree (id_book);


--
-- TOC entry 2036 (class 1259 OID 51958)
-- Name: fki_books_fk; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX fki_books_fk ON write USING btree (id_book);


--
-- TOC entry 2039 (class 1259 OID 51933)
-- Name: fki_id_books_fk; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX fki_id_books_fk ON loans USING btree (id_book);


--
-- TOC entry 2040 (class 1259 OID 51939)
-- Name: fki_id_users_fk; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX fki_id_users_fk ON loans USING btree (id_user);


--
-- TOC entry 2046 (class 2606 OID 51947)
-- Name: authors_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY write
    ADD CONSTRAINT authors_fk FOREIGN KEY (id_author) REFERENCES authors(id_aut) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 2050 (class 2606 OID 51917)
-- Name: book_isbn_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY bookings
    ADD CONSTRAINT book_isbn_fk FOREIGN KEY (id_book) REFERENCES books(isbn) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 2047 (class 2606 OID 51953)
-- Name: books_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY write
    ADD CONSTRAINT books_fk FOREIGN KEY (id_book) REFERENCES books(isbn) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 2048 (class 2606 OID 51928)
-- Name: id_books_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY loans
    ADD CONSTRAINT id_books_fk FOREIGN KEY (id_book) REFERENCES books(isbn) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 2049 (class 2606 OID 51934)
-- Name: id_users_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY loans
    ADD CONSTRAINT id_users_fk FOREIGN KEY (id_user) REFERENCES users(fiscal_code) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 2051 (class 2606 OID 51923)
-- Name: user_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY bookings
    ADD CONSTRAINT user_fk FOREIGN KEY (id_user) REFERENCES users(fiscal_code) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 2173 (class 0 OID 0)
-- Dependencies: 6
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


-- Completed on 2018-01-30 01:14:38

--
-- PostgreSQL database dump complete
--

