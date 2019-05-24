



drop table if exists Odbior
drop table if exists Logowanie
drop table if exists Darowizna
drop table if exists Dane
drop table if exists Rezerwacja
drop table if exists Pies
drop table if exists Uzytkownik
drop table if exists Opiekun
drop table if exists Badanie
drop table if exists Rasa

create table Uzytkownik (
id int identity primary key,
login varchar(6) unique not null,
haslo varchar(8) not null,
upowaznienia varchar(5) not null
)

insert into Uzytkownik(login, haslo, upowaznienia) values ('adnub', 'admin', 'A')

create table Rasa (
id int identity primary key,
nazwa_pl varchar(20),
nazwa_ang varchar(20)
)

insert into Rasa (nazwa_pl, nazwa_ang) values ('Kundel', 'Mongrel')

create table Opiekun (
id int identity primary key,
imie varchar(10) not null,
nazwisko varchar(10) not null
)

create table Pies (
id int identity primary key,
imie varchar(10) not null,
id_rasa int foreign key references Rasa(id) default 1,
id_opiekun int foreign key references Opiekun(id),
is_lagodny BIT default 0,
is_wege BIT default 0,
choroby varchar(30),
)

create table Badanie (
id int identity primary key,
data date,
opis varchar(30)
)

create table Rezerwacja (
id int identity primary key,
data date not null default getdate(),
data_do date not null,
id_pies int foreign key references Pies(id) not null,
id_uzytkownik	int foreign key references Uzytkownik(id)
)

create table Odbior (
id int identity primary key,
data date not null default getdate(),	
id_rezerwacja int foreign key references Rezerwacja(id)
)

create table Dane (
id int identity primary key,
imie varchar(10) not null,
nazwisko varchar(10) not null,
telefon varchar(10) not null,
ulica varchar(15) not null,
nr_domu varchar(5) not null,
is_sponsor BIT default 0,
id_uzytkownik int foreign key references Uzytkownik(id) not null
)

create table Darowizna (
id int identity primary key,
kwota int not null,
data date not null default getdate(),
id_uzytkownik int foreign key references Uzytkownik(id)
)

create table Logowanie (
id int identity primary key,
data datetime default getdate(),
id_uzytkownik int foreign key references Uzytkownik(id) not null
)

select * from Uzytkownik

select * from Logowanie

select Logowanie.id, Uzytkownik.login, Logowanie.data from Logowanie inner join Uzytkownik on Logowanie.id_uzytkownik = Uzytkownik.id
