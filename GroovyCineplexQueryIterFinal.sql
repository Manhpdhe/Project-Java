/* 
	Force delete the database every time this SQL file is executed. (if it exists)
*/
USE master;
IF db_id('GroovyCineplexDB') IS NOT NULL
ALTER DATABASE [GroovyCineplexDB] SET SINGLE_USER WITH ROLLBACK IMMEDIATE;
DROP DATABASE [GroovyCineplexDB];
go

/* 
	Create database
*/
CREATE DATABASE [GroovyCineplexDB];
go
use [GroovyCineplexDB];

/* 
	Create tables
*/

create table [Gender]
(
	GenderID int PRIMARY KEY NOT NULL IDENTITY(1, 1),
	GenderName nvarchar(128)
)

/* Cinema Branch */
create table [Branch]
(
	BranchID int PRIMARY KEY NOT NULL IDENTITY(1, 1),
	[Location] nvarchar(128),
	BranchName nvarchar(128)
)

create table ManagerRole
(
    ManagerRoleID int PRIMARY KEY NOT NULL IDENTITY(1, 1),
	RoleName varchar(256)
)

create table [Manager] 
(
	ManagerID int PRIMARY KEY NOT NULL IDENTITY(1, 1),
	[AccountName] varchar(256) UNIQUE,
	[Password] varchar(256),
	FirstName nvarchar(256),
	LastName nvarchar(256),
	GenderID int FOREIGN KEY references Gender(GenderID),
	Email varchar(128) UNIQUE,
	DOB date,
	[Address] nvarchar(512),
	PhoneNumber varchar(32) UNIQUE,
	ManagerRoleID int FOREIGN KEY references ManagerRole(ManagerRoleID),
	BranchID int FOREIGN KEY references Branch(BranchID),
	Verified bit
)



create table [Customer] 
(
	CustomerID int PRIMARY KEY NOT NULL IDENTITY(1, 1),
	[AccountName] varchar(256) UNIQUE,
	[Password] varchar(256),
	FirstName nvarchar(256),
	LastName nvarchar(256),
	GenderID int FOREIGN KEY references Gender(GenderID),
	Email varchar(128) UNIQUE,
	DOB date,
	[Address] nvarchar(512),
	PhoneNumber varchar(32) UNIQUE,
	Verified bit
)

create table [Verification]
(
	ManagerID int FOREIGN KEY references Manager(ManagerID),
	CustomerID int FOREIGN KEY references Customer(CustomerID),
	Code varchar(10) NOT NULL,
	CreateTime datetime NOT NULL,
	CONSTRAINT ck_OnlyManagerOrCus CHECK ((ManagerID IS NULL AND CustomerID IS NOT NULL) OR (ManagerID IS NOT NULL AND CustomerID IS NULL))
)
/*
create table [Feedback]
(
	FeedbackID int PRIMARY KEY NOT NULL IDENTITY(1, 1),
	UserID int FOREIGN KEY references [User](UserID),
	FeedbackTitle nvarchar(1024),
	FeedbackDescription ntext,
	CreatedTime datetime,
)
*/


create table Room
(
	RoomID int PRIMARY KEY NOT NULL IDENTITY(1, 1),
	RoomName varchar(5),
	NumRows int,
	NumCols int,
	BranchID int FOREIGN KEY references Branch(BranchID)
)


create table ConfirmedTicket
(
	ConfirmedTicketID int PRIMARY KEY NOT NULL IDENTITY(1, 1),

)


create table [Order]
(
	OrderID int PRIMARY KEY NOT NULL IDENTITY(1, 1),
	CustomerID int FOREIGN KEY references Customer(CustomerID),
	OrderTime datetime,
	PaymentURL varchar(1024),
	CONSTRAINT UK_customer_order UNIQUE(CustomerID, OrderTime)
)

create table SeatType
(
	SeatTypeID int PRIMARY KEY NOT NULL IDENTITY(1, 1),
	SeatTypeName nvarchar(256)
)

create table TicketPrice
(
	TicketPriceID int PRIMARY KEY NOT NULL IDENTITY(1, 1),
	SeatTypeID int FOREIGN KEY references SeatType(SeatTypeID),
	Price decimal,
	CreateTime datetime
)


create table Seat
(
	SeatID int PRIMARY KEY NOT NULL IDENTITY(1, 1),
	RowNo int,
	ColNo int,
	RealRowNo int,
	RealColNo int,
	SeatTypeID int FOREIGN KEY references SeatType(SeatTypeID),
	RoomID int FOREIGN KEY references Room(RoomID),
	CONSTRAINT UK_RowSeatRoom UNIQUE(RowNo, RealColNo, RoomID)
)


create table [Rating]
(
	RatingID int PRIMARY KEY NOT NULL IDENTITY(1, 1),
	RatingLabel varchar(32),
	RatingDesc ntext
)

create table Genre
(
	GenreID int PRIMARY KEY NOT NULL IDENTITY(1, 1),
	GenreName nvarchar(256)
)

create table [Language]
(
	LanguageID int PRIMARY KEY NOT NULL IDENTITY(1, 1),
	LanguageCode varchar(5),
	LanguageName nvarchar(256)
)

INSERT INTO [Language] VALUES
('vi_VN','Tiếng Việt')

create table Movie
(
	MovieID int PRIMARY KEY NOT NULL IDENTITY(1, 1),
	Title nvarchar(256),
	ReleaseDate date,
	Duration int,
	RatingID int FOREIGN KEY REFERENCES Rating(RatingID),
	MovieDesc ntext,
	Poster varchar(256),
	[LanguageID] int FOREIGN KEY REFERENCES [Language](LanguageID),
	[View] int default(0),
	Background varchar(256),
	[URL] nvarchar(max)
	
)

create table WatchList
(
	WatchListID int PRIMARY KEY NOT NULL IDENTITY(1, 1),
	CustomerID int FOREIGN KEY references Customer(CustomerID)
)

create table WatchListMovie
(
	WatchListID int FOREIGN KEY references WatchList(WatchListID),
	MovieID int FOREIGN KEY references Movie(MovieID)
)

create table [Notification]
(
    NotificationID int PRIMARY KEY NOT NULL IDENTITY(1, 1),
	ManagerID int FOREIGN KEY REFERENCES Manager(ManagerID),
	MovieID int FOREIGN KEY REFERENCES Movie(MovieID)
)


create table Classify
(
	MovieID int FOREIGN KEY REFERENCES Movie(MovieID),
	GenreID int FOREIGN KEY REFERENCES Genre(GenreID)
)


create table [Director]
(
	DirectorID int PRIMARY KEY NOT NULL IDENTITY(1, 1),
	FirstName nvarchar(256),
	LastName nvarchar(256),
	DOB date
)

create table [Actor]
(
	ActorID int PRIMARY KEY NOT NULL IDENTITY(1, 1),
	FirstName nvarchar(256),
	LastName nvarchar(256),
	DOB date
)






create table [Cast]
(
	ActorID int FOREIGN KEY REFERENCES [Actor](ActorID),
	MovieID int FOREIGN KEY REFERENCES [Movie](MovieID),
	CONSTRAINT UK_ActorCastMovie UNIQUE (ActorID, MovieID)
)

create table [Direct]
(
	DirectorID int FOREIGN KEY REFERENCES [Director](DirectorID),
	MovieID int FOREIGN KEY (MovieID) REFERENCES [Movie](MovieID),
	CONSTRAINT UK_DirectorDirectMovie UNIQUE (DirectorID, MovieID)
)


/*
create table MovieFeedback
(
	MovieFeedbackID int PRIMARY KEY NOT NULL IDENTITY(1, 1),
	UserID int FOREIGN KEY references [User](UserID),
	MovieID int FOREIGN KEY references Movie(MovieID),
	Stars int,
	CommentDesc ntext,
	FeedbackTime datetime
)



create table ReactEmote
(
	ReactEmoteID int PRIMARY KEY NOT NULL IDENTITY(1, 1),
	ReactName nvarchar(128),
	ReactImagePath nvarchar(512)
)

create table React
(
	MovieFeedbackID int FOREIGN KEY references [MovieFeedback](MovieFeedbackID),
	ReactEmoteID int FOREIGN KEY references [ReactEmote](ReactEmoteID)
)
*/

create table FormatType
(
	FormatTypeID int PRIMARY KEY NOT NULL IDENTITY(1, 1),
	FormatName varchar(32),
)

create table TimeSlot
(
	TimeSlotID int PRIMARY KEY NOT NULL IDENTITY(1, 1),
	StartTime datetime,
	EndTime datetime,
	RoomID int FOREIGN KEY references Room(RoomID)

)

create table Membership
(
	MembershipID int PRIMARY KEY NOT NULL IDENTITY(1, 1),
	CardNumber varchar(16) UNIQUE,
	CustomerID int FOREIGN KEY references Customer(CustomerID) UNIQUE,
	RegisterDate date,
	Points bigint
)

create table Show
(
	ShowID int PRIMARY KEY NOT NULL IDENTITY(1, 1),
	StartDelay int,
	[Status] bit,
	MovieID int FOREIGN KEY references Movie(MovieID),
	TimeSlotID int FOREIGN KEY references TimeSlot(TimeSlotID) UNIQUE,
)

create table CaptionType
(
	CaptionTypeID int PRIMARY KEY NOT NULL IDENTITY(1, 1),
	CaptionTypeName nvarchar(128)
)

create table ShowCaption
(
	ShowID int FOREIGN KEY references Show(ShowID),
	CaptionTypeID int FOREIGN KEY references CaptionType(CaptionTypeID),
)

create table Ticket
(
	TicketID int PRIMARY KEY NOT NULL IDENTITY(1, 1),
	OrderID int FOREIGN KEY references [Order](OrderID),
	TicketPriceID int NOT NULL FOREIGN KEY references [TicketPrice](TicketPriceID),
	ShowID int NOT NULL FOREIGN KEY references [Show](ShowID),
	SeatID int NOT NULL FOREIGN KEY references [Seat](SeatID),
	CONSTRAINT UK_Ticket UNIQUE (ShowID, SeatID)
)

create table TicketHistory
(
	TicketHistoryID int PRIMARY KEY NOT NULL IDENTITY(1, 1),
	OrderCode varchar(128) UNIQUE,
	SeatsString varchar(512),
	TimeString varchar(512),
	OrderTime datetime,
	CustomerID int FOREIGN KEY references [Customer](CustomerID),
	MovieName nvarchar(512),
	RoomName nvarchar(16),
	BranchName nvarchar(512),
	TotalPrice decimal,
	PaymentStatus int,
	PaymentLink varchar(max)

)

create table [Format]
(
	ShowID int FOREIGN KEY references Show(ShowID),
	FormatTypeID int FOREIGN KEY references [FormatType](FormatTypeID)
)
/*
create table SeatReserved
(
	SeatReservedID int FOREIGN KEY references Seat(SeatID),
	TicketID int FOREIGN KEY references [Ticket](TicketID),
	ShowID int FOREIGN KEY references [Show](ShowID),
	[ReleaseStatus] bit,
	PRIMARY KEY (SeatReservedID, TicketID)
)
*/



/* BACKUP
'Peyton Reed', 'Kathryn Newton, Michael Douglas, Evangeline Lilly,...'
'Joel Crawford, Januel Mercado', 'DaVine Joy Randolph, Wagner Moura, Samson Kayo,...', 
'James Cameron', 'Kate Winslet, Giovanni Ribisi, Stephen Lang,...',
'Michael B. Jordan', 'Florian Munteanu, Tessa Thompson, Wood Harris, Michael B. Jordan, Phylicia Rashad',
'Elizabeth Banks', 'Kristofer Hivju, Brooklynn Prince, Jesse Tyler Ferguson,...'
*/

INSERT INTO Rating VALUES
('P',N'Films are allowed to be widely distributed to all audiences'),
('C13',N'Prohibited films from being released to audiences under the age of 13'),
('C16',N'Prohibited films from being released to audiences under the age of 16'),
('C18',N'Prohibited films from being released to audiences under the age of 18');

insert into [Branch] values 
(N'Quang Ninh', N'Groovy Cineplex Quang Ninh'),
(N'Ha Noi', N'Groovy Cineplex Ha Noi'),
(N'Sai Gon', N'Groovy Cineplex Sai Gon');

INSERT INTO Movie VALUES
	('Ant-Man and The Wasp: Quantumania', '17 February 2023', 117, 2, 'Superhero partners Scott Lang (Paul Rudd) and Hope Van Dyne (Evangeline Lilly) return to continue their adventures as Ant-Man and the Wasp. Together, with Hope’s parents Hank Pym (Michael Douglas) and Janet Van Dyne (Michelle Pfeiffer), and Scott’s daughter Cassie Lang (Kathryn Newton), the family finds themselves exploring the Quantum Realm, interacting with strange new creatures and embarking on an adventure that will push them beyond the limits of what they thought possible. ANT-MAN AND THE WASP: QUANTUMANIA contains several sequences with flashing lights that may affect those who are susceptible to photosensitive epilepsy or have other photosensitivities.','poster/antman.png',1,0,'poster/antman-bg.png','https://www.youtube.com/watch?v=FKGy8501GOY'),
	('Puss in Boots: The Last Wish', '3 February 2023', 90, 1, 'This year, everyone’s favourite leche-loving, swashbuckling, fear-defying feline returns. For the first time in more than a decade, DreamWorks Animation presents a new adventure in the Shrek universe as daring outlaw Puss in Boots discovers that his passion for peril and disregard for safety have taken their toll. Puss has burned through eight of his nine lives, though he lost count along the way. Getting those lives back will send Puss in Boots on his grandest quest yet. But with only one life left, Puss will have to humble himself and ask for help from his former partner and nemesis: the captivating Kitty Soft Paws (Oscar® nominee Salma Hayek). Together, our trio of heroes will have to stay one step ahead of Goldilocks (Oscar® nominee Florence Pugh, Black Widow) and the Three Bears Crime Family, “Big” Jack Horner (Emmy winner John Mulaney, Big Mouth) and terrifying bounty hunter, The Big Bad Wolf (Wagner Moura, Narcos).','poster/pib.png',1,0,'poster/pib-bg.png','https://www.youtube.com/watch?v=5ZdgiQBi3NM'),
	('Avatar: The Way of Water',  '16 December 2022', 162, 2, 'Experience the once-in-a-generation movie event, exclusively in cinemas. Set more than a decade after the events of the first film, AVATAR 2 begins to tell the story of the Sully family (Jake, Neytiri, and their kids), the trouble that follows them, the lengths they go to keep each other safe, the battles they fight to stay alive and the tragedies they endure. The latest in the groundbreaking fantasy saga from director James Cameron.','poster/avatar.png',1,0,'poster/avatar-bg.png','https://www.youtube.com/watch?v=bf4yyStDWHE'),
	('Creed III',  '3 March 2023', 116, 2, 'After dominating the boxing world, Adonis Creed (Michael B. Jordan) has been thriving in both his career and family life. When a childhood friend and former boxing prodigy, Damian (Jonathan Majors), resurfaces after serving a long sentence in prison, he is eager to prove that he deserves his shot in the ring. The face-off between former friends is more than just a fight. To settle the score, Adonis must put his future on the line to battle Damian – a fighter who has nothing to lose. Creed III is the third installment in the successful franchise and is Michael B. Jordan’s directorial debut.','poster/creed.png',1,0,'poster/creed-bg.png','https://www.youtube.com/watch?v=ir0lrq1IBtw'),
	('Cocaine Bear', '24 February 2023', 95, 4, 'Inspired by the 1985 true story of a drug runners plane crash, missing cocaine, and the black bear that ate it, this wild dark comedy finds an oddball group of cops, criminals, tourists, and teens converging in a Georgia forest where a 500- pound apex predator has ingested a staggering amount of cocaine and gone on a coke-fueled rampage for more blow … and blood.','poster/bear.png',1,0,'poster/bear-bg.png','https://www.youtube.com/watch?v=DuWEEKeJLMI'),
	('Indiana Jones 5', '2023-07-07', 135, 3, 'Famed archaeologist and adventurer Indiana Jones (Harrison Ford) returns for a new action-packed adventure. Set in the 1960s, Indy sets out to uncover ancient mysteries and face dangerous adversaries in a race against time.','poster/indiana_jones.png',1,0,'poster/indiana_jones-bg.png','https://www.youtube.com/watch?v=NBEr-EnJWUg'),
    ('Minions: The Rise of Gru', '2023-06-30', 120, 1, 'Travel back in time to witness the origins of Gru and the adorable Minions. Follow the journey of young Gru as he embarks on his villainous path, accompanied by the ever-loyal and mischievous Minions.','poster/minions.png',1,0,'poster/minions-bg.png','https://www.youtube.com/watch?v=XIEB4a1yjbw'),
    ('The Flash', '2023-11-02', 145, 2, 'Barry Allen (Ezra Miller) returns as the Scarlet Speedster in this action-packed DC superhero film. Discover the true potential of The Flash as he navigates through the complexities of time travel and confronts powerful adversaries.','poster/the_flash.png',1,0,'poster/the_flash-bg.png','https://www.youtube.com/watch?v=IbTxJhITPDM'),
    ('Toy Story 5', '2023-12-21', 100, 1, 'Woody, Buzz Lightyear, and the gang return for an all-new heartwarming adventure. Join your favorite toys as they embark on a journey filled with laughter, tears, and the true meaning of friendship.','poster/toy_story.png',1,0,'poster/toy_story-bg.png','https://www.youtube.com/watch?v=8lLWQHtR0RA'),
    ('The Batman', '2023-10-05', 150, 3, 'Bruce Wayne (Robert Pattinson) takes on the mantle of the Dark Knight in this gritty and dark portrayal of Batman. Witness the rise of a new hero as he battles Gotham City’s most notorious villains.','poster/the_batman.png',1,0,'poster/the_batman-bg.png','https://www.youtube.com/watch?v=Q7tEaw6z2r4'),
    ('Guardians of the Galaxy Vol. 3', '2023-05-04', 160, 2, 'Star-Lord, Gamora, Drax, Rocket, and Groot return for another cosmic adventure. Join the Guardians of the Galaxy as they face new challenges, make unexpected alliances, and save the universe once again.','poster/guardians_of_the_galaxy.png',1,0,'poster/guardians_of_the_galaxy-bg.png','https://www.youtube.com/watch?v=1ZHq3eTgeCg'),
    ('Madagascar 4', '2023-12-14', 95, 1, 'Alex, Marty, Gloria, and Melman are back for more wild and hilarious escapades. This time, they find themselves on a thrilling journey through new lands and encountering eccentric characters along the way.','poster/madagascar.png',1,0,'poster/madagascar-bg.png','https://www.youtube.com/watch?v=cdxEhFTY2KE'),
    ('Spider-Man: Freshman Year', '2023-08-17', 130, 2, 'Experience the untold origin story of Peter Parker as Spider-Man. Follow young Peter as he learns to harness his newfound powers and faces the challenges of being a teenage superhero.','poster/spiderman.png',1,0,'poster/spiderman-bg.png','https://www.youtube.com/watch?v=_fZJDH0EJ5o'),
    ('The Incredibles 3', '2023-11-24', 115, 1, 'The super-powered Parr family returns for another action-packed adventure. Join Mr. Incredible, Elastigirl, Violet, Dash, and Jack-Jack as they face new supervillains and family challenges.','poster/incredibles.png',1,0,'poster/incredibles-bg.png','https://www.youtube.com/watch?v=I-9MSjQq6f0'),
    ('Shrek 5', '2023-07-21', 105, 1, 'The beloved ogre Shrek is back for a new hilarious and heartwarming journey. Join Shrek, Fiona, Donkey, and Puss in Boots as they encounter new fairy-tale characters and embark on a quest to save the kingdom.','poster/shrek.png',1,0,'poster/shrek-bg.png','https://www.youtube.com/watch?v=AhJYpKKpLm4'),
    ('Wolverine: Berserker', '2023-09-14', 140, 3, 'Logan, aka Wolverine (Hugh Jackman), returns for one last berserker rage in this emotionally charged and action-packed farewell to the iconic X-Men character.','poster/wolverine.png',1,0,'poster/wolverine-bg.png','https://www.youtube.com/watch?v=bsLPIQOlD1A'),
    ('Avatar: The Seed Bearer', '2023-12-21', 170, 2, 'The Avatar saga continues as new characters and clans emerge in the lush world of Pandora. Witness the next chapter in the epic journey to protect Pandora and its unique ecosystem.','poster/avatar2.png',1,0,'poster/avatar2-bg.png','https://www.youtube.com/watch?v=JITn3RvyF-s'),
    ('Wonder Woman 3', '2023-11-30', 155, 2, 'Diana Prince (Gal Gadot) returns as Wonder Woman in this thrilling and empowering new chapter. Join Wonder Woman as she faces formidable foes and fights for justice.','poster/wonder_woman.png',1,0,'poster/wonder_woman-bg.png','https://www.youtube.com/watch?v=decrM41c1XM'),
    ('A Quiet Place Part III', '2023-06-23', 120, 4, 'In a world where silence is survival, the Abbott family must once again navigate through deadly threats in this suspenseful horror thriller.','poster/a_quiet_place.png',1,0,'poster/a_quiet_place-bg.png','https://www.youtube.com/watch?v=cklKhvl5PmI'),
    ('Jumanji: Beyond the Jungle', '2023-07-14', 125, 1, 'The adventure continues as new players enter the treacherous world of Jumanji. Prepare for a thrilling ride filled with danger, excitement, and unexpected twists.','poster/jumanji.png',1,0,'poster/jumanji-bg.png','https://www.youtube.com/watch?v=Z8rCRw8e8jc'),
    ('The Lion King II: Simba`s Pride', '2023-11-17', 110, 1, 'Return to the Pride Lands in this long-awaited sequel to The Lion King. Follow Simba and Nala`s daughter Kiara as she embarks on her own journey of self-discovery and love.','poster/lion_king.png',1,0,'poster/lion_king-bg.png','https://www.youtube.com/watch?v=7L-tNwAwmYI'),
    ('Fast & Furious 11', '2023-07-28', 160, 4, 'The adrenaline-fueled Fast & Furious franchise continues with even more high-octane action, heart-stopping stunts, and intense family drama.','poster/fast_and_furious.png',1,0,'poster/fast_and_furious-bg.png','https://www.youtube.com/watch?v=8tPnX7OPo0Q'),
    ('Pirates of the Caribbean 6', '2023-12-15', 145, 3, 'Captain Jack Sparrow (Johnny Depp) returns for a new swashbuckling adventure on the high seas. Set sail with Jack as he faces old foes and new threats in search of legendary treasures.','poster/pirates_of_the_caribbean.png',1,0,'poster/pirates_of_the_caribbean-bg.png','https://www.youtube.com/watch?v=B-7HxG-9vzk'),
    ('Zootopia 2', '2023-11-09', 115, 1, 'Judy Hopps and Nick Wilde return for a new case in the bustling city of Zootopia. Join our dynamic duo as they unravel a mystery that could change the city forever.','poster/zootopia.png',1,0,'poster/zootopia-bg.png','https://www.youtube.com/watch?v=5dhWxXrOyMU'),
    ('Mulan II', '2023-12-28', 105, 1, 'Mulan and her friends embark on a new adventure to protect the emperor of China and bring honor to their families.','poster/mulan.png',1,0,'poster/mulan-bg.png','https://www.youtube.com/watch?v=1Fm7ts3rOcA'),
    ('Scream 5', '2023-10-19', 120, 4, 'Ghostface returns to terrorize a new generation in this chilling sequel to the iconic horror franchise. Get ready for screams, shocks, and surprises.','poster/scream.png',1,0,'poster/scream-bg.png','https://www.youtube.com/watch?v=stRyJqw1fBg'),
    ('Kung Fu Panda 4', '2023-07-12', 100, 1, 'Po and the Furious Five are back to defend the Valley of Peace from new threats. Join them on a hilarious and action-packed journey.','poster/kung_fu_panda.png',1,0,'poster/kung_fu_panda-bg.png','https://www.youtube.com/watch?v=MmHzch-mzrE'),
    ('The Conjuring 4', '2023-10-26', 125, 4, 'The Warrens return for another bone-chilling paranormal investigation in this fourth installment of The Conjuring series.','poster/conjuring.png',1,0,'poster/conjuring-bg.png','https://www.youtube.com/watch?v=7fi6_iOHvR4'),
    ('Mad Max: Furiosa', '2023-12-07', 150, 3, 'Discover the origin story of Furiosa (Anya Taylor-Joy) in this high-octane prequel to Mad Max: Fury Road. Witness her journey from captive to fierce warrior.','poster/mad_max.png',1,0,'poster/mad_max-bg.png','https://www.youtube.com/watch?v=HZNUwd6XjKk'),
    ('Finding Marlin', '2023-06-16', 105, 1, 'Marlin and Dory are on a new adventure to find Marlins long-lost son Nemo, who has gone missing once again.','poster/finding_marlin.png',1,0,'poster/finding_marlin-bg.png','https://www.youtube.com/watch?v=r-29TN5s01I'),
    ('Soul 2', '2023-11-16', 110, 1, 'Joe Gardner returns for another soul-stirring adventure in this sequel to the critically acclaimed Soul. Follow Joe as he explores new realms of existence and the power of music.','poster/soul.png',1,0,'poster/soul-bg.png','https://www.youtube.com/watch?v=fb0WOTaITBs'),
    ('The Matrix Resurrections', '2023-12-21', 160, 4, 'Neo (Keanu Reeves) awakens to a new reality and must once again confront the forces of the Matrix. Follow Neo on his mind-bending journey of self-discovery and revolution.','poster/matrix.png',1,0,'poster/matrix-bg.png','https://www.youtube.com/watch?v=Z9A4KQQfRVs'),
    ('Fantastic Four: Reborn', '2023-10-19', 140, 2, 'Marvels First Family returns with a fresh start and new powers. Witness the epic origins of Mr. Fantastic, Invisible Woman, Human Torch, and The Thing.','poster/fantastic_four.png',1,0,'poster/fantastic_four-bg.png','https://www.youtube.com/watch?v=TQK8BgiPb3I'),
    ('The Chronicles of Narnia: The Silver Chair', '2023-12-14', 130, 1, 'Eustace Scrubb and Jill Pole journey to Narnia to save Prince Rilian from the enchantments of the Silver Chair.','poster/narnia.png',1,0,'poster/narnia-bg.png','https://www.youtube.com/watch?v=wcWVhvAuE84'),
    ('Frozen III', '2023-11-22', 115, 1, 'Anna, Elsa, Kristoff, Olaf, and Sven return for a new icy adventure filled with love, magic, and self-discovery.','poster/frozen.png',1,0,'poster/frozen-bg.png','https://www.youtube.com/watch?v=RZf8gE2s8H8'),
    ('The Expendables 4', '2023-08-24', 150, 4, 'The ultimate action ensemble returns for more explosive and adrenaline-fueled missions.','poster/expendables.png',1,0,'poster/expendables-bg.png','https://www.youtube.com/watch?v=jQq1pNjcCOg'),
    ('The Little Mermaid', '2023-12-14', 125, 1, 'Ariel embarks on a daring adventure on land and sea to find true love and her place in the world.','poster/little_mermaid.png',1,0,'poster/little_mermaid-bg.png','https://www.youtube.com/watch?v=78ttz63MUTM'),
    ('Blade: The Daywalker', '2023-09-21', 135, 4, 'The vampire hunter Blade (Mahershala Ali) returns to protect humanity from the undead scourge.','poster/blade.png',1,0,'poster/blade-bg.png','https://www.youtube.com/watch?v=N93WSUaOIjs'),
    ('Shang-Chi and the Legend of the Ten Rings 2', '2023-11-09', 140, 2, 'Shang-Chi must face a new threat that puts his martial arts skills to the ultimate test.','poster/shang_chi.png',1,0,'poster/shang_chi-bg.png','https://www.youtube.com/watch?v=Z0LK-V7Kxiw'),
    ('The Flashpoint Paradox', '2023-12-07', 145, 2, 'Barry Allens journey through the multiverse takes an unexpected turn in this mind-bending DC superhero film.','poster/flashpoint.png',1,0,'poster/flashpoint-bg.png','https://www.youtube.com/watch?v=Cv-Mvz7QjS8'),
    ('Jungle Cruise 2', '2023-08-03', 130, 1, 'Emily Blunt and Dwayne Johnson return for another thrilling and adventurous river expedition.','poster/jungle_cruise.png',1,0,'poster/jungle_cruise-bg.png','https://www.youtube.com/watch?v=xhHrrFOm-Qw'),
    ('Hocus Pocus 2', '2023-10-12', 110, 1, 'The Sanderson Sisters return for another witchy adventure in this highly anticipated sequel.','poster/hocus_pocus.png',1,0,'poster/hocus_pocus-bg.png','https://www.youtube.com/watch?v=BplcRpqkJrs'),
    ('Tangled: Ever After', '2023-11-23', 105, 1, 'Rapunzel and Flynn Rider embark on a new journey after their wedding ceremony.','poster/tangled.png',1,0,'poster/tangled-bg.png','https://www.youtube.com/watch?v=hJJ3bSOPi6I'),
    ('The Kings Man 2', '2023-12-28', 140, 3, 'The origins of the Kingsman agency continue to unfold in this action-packed spy thriller.','poster/kingsman.png',1,0,'poster/kingsman-bg.png','https://www.youtube.com/watch?v=2lTmOc8Y4Kw'),
    ('Doctor Strange in the Multiverse of Madness', '2023-05-03', 155, 2, 'Doctor Strange faces mystical and otherworldly threats as he explores the multiverse.','poster/doctor_strange.png',1,0,'poster/doctor_strange-bg.png','https://www.youtube.com/watch?v=lazF9CZapx0'),
    ('Jurassic World: The New Era', '2023-06-21', 150, 2, 'Return to Isla Nublar as new dinosaurs and unforeseen dangers await.','poster/jurassic_world.png',1,0,'poster/jurassic_world-bg.png','https://www.youtube.com/watch?v=bnQawWCYf-Q'),
    ('Aquaman and the Lost City', '2023-12-21', 160, 2, 'Aquaman sets out on a perilous quest to find the legendary Lost City of Atlantis.','poster/aquaman.png',1,0,'poster/aquaman-bg.png','https://www.youtube.com/watch?v=qzDZ_dPz7Mw'),
    ('The Black Panther: Wakanda Forever', '2023-07-05', 150, 2, 'The legacy of TChalla lives on as Wakanda faces new challenges and threats.','poster/black_panther.png',1,0,'poster/black_panther-bg.png','https://www.youtube.com/watch?v=59qs1Rxu8iY'),
    ('Thor: God of Thunder', '2023-11-02', 160, 2, 'Thor, the God of Thunder, battles ancient cosmic forces to protect the Nine Realms.','poster/thor.png',1,0,'poster/thor-bg.png','https://www.youtube.com/watch?v=ksBhj8ySg48'),
    ('The Greatest Showman 2', '2023-12-20', 120, 1, 'P.T. Barnums circus returns for more extraordinary performances and dazzling spectacles.','poster/greatest_showman.png',1,0,'poster/greatest_showman-bg.png','https://www.youtube.com/watch?v=7VfDZMZsCbU'),
    ('The Conjuring Universe: Annabelle`s Return', '2023-10-12', 125, 4, 'The malevolent doll Annabelle returns to terrorize new victims in this spine-chilling horror installment.','poster/annabelle.png',1,0,'poster/annabelle-bg.png','https://www.youtube.com/watch?v=lB88CBwAwWU'),
    ('Men in Black: Galaxy Defenders', '2023-11-16', 130, 1, 'The Men in Black return to protect the Earth from intergalactic threats.','poster/men_in_black.png',1,0,'poster/men_in_black-bg.png','https://www.youtube.com/watch?v=6ZgTmf3M2lg'),
    ('Pacific Rim: Uprising', '2023-07-26', 135, 2, 'Jaegers rise once again to defend humanity against colossal Kaiju monsters.','poster/pacific_rim.png',1,0,'poster/pacific_rim-bg.png','https://www.youtube.com/watch?v=o_HFnHzbf3Q');




INSERT INTO Director VALUES 
('John', 'Doe', '1975-08-10'),
 ('Jane', 'Smith', '1980-05-22'),
 ('Michael', 'Johnson', '1978-11-30'),
 ('Emily', 'Williams', '1982-04-15'),
 ('Robert', 'Brown', '1972-09-18'),
 ('Sarah', 'Lee', '1985-07-12'),
 ('David', 'Davis', '1976-12-05'),
 ('Jennifer', 'White', '1983-03-28'),
 ('Matthew', 'Anderson', '1979-06-20'),
 ('Laura', 'Martinez', '1981-10-08'),
('Christopher', 'Taylor', '1974-02-14'),
 ('Melissa', 'Thomas', '1987-09-25'),
 ('James', 'Harris', '1973-01-17'),
('Jessica', 'Clark', '1984-12-01'),
 ('William', 'Allen', '1986-08-09'),
 ('Amanda', 'Lewis', '1977-07-30'),
 ('Daniel', 'Scott', '1988-03-12'),
 ('Elizabeth', 'Green', '1989-06-28'),
 ('Christopher', 'Johnson', '1983-11-05'),
 ('Lauren', 'Turner', '1984-07-19'),
 ('Ryan', 'Martinez', '1990-04-02'),
 ('Stephanie', 'Harris', '1992-01-08'),
('Andrew', 'Lee', '1982-12-15')

INSERT INTO Direct (DirectorID, MovieID) VALUES 
(1, 1),
(2, 2),
(3, 3),
(4, 4),
(5, 5),
(6, 6),
(7, 7),
(8, 8),
(9, 9),
(10, 10),
(11, 11),
(12, 12),
(13, 13),
(14, 14),
(15, 15),
(16, 16),
(17, 17),
(18, 18),
(19, 19),
(20, 20),
(21, 21),
(22, 22),
(23, 23),
(1, 24),
(2, 25),
(3, 26),
(4, 27),
(5, 28),
(6, 29),
(7, 30),
(8, 31),
(9, 32),
(10, 33),
(11, 34),
(12, 35),
(13, 36),
(14, 37),
(15, 38),
(16, 39),
(17, 40),
(18, 41),
(19, 42),
(20, 43),
(21, 44),
(22, 45),
(23, 46),
(1, 47),
(2, 48),
(3, 49),
(4, 50),
(5, 51),
(6, 52)

/*
INSERT INTO Branch VALUES (N'Hà Nội',N'Long Biên'), (N'TP. Hồ Chí Minh',N'Quận 1'), (N'Đà Nẵng',N'Hải Châu');

INSERT INTO Room (RoomName, NoOfSeats) VALUES (1, 50), (2, 30), (3, 30), (4, 30), (5, 30);

INSERT INTO Seat (RowNo, SeatNo, RoomID) VALUES ('A', 1, 1), ('A', 2, 1), ('A', 3, 1), ('A', 4, 1), ('A', 5, 1), ('A', 6, 1), ('A', 7, 1), ('A', 8, 1), ('A', 9, 1), ('A', 10, 1), ('B', 1, 1), ('B', 2, 1), ('B', 3, 1), ('B', 4, 1), ('B', 5, 1), ('B', 6, 1), ('B', 7, 1), ('B', 8, 1), ('B', 9, 1), ('B', 10, 1), ('C', 1, 1), ('C', 2, 1), ('C', 3, 1), ('C', 4, 1), ('C', 5, 1), ('C', 6, 1), ('C', 7, 1), ('C', 8, 1), ('C', 9, 1), ('C', 10, 1), 
												('D', 1, 1), ('D', 2, 1), ('D', 3, 1), ('D', 4, 1), ('D', 5, 1), ('D', 6, 1), ('D', 7, 1), ('D', 8, 1), ('D', 9, 1), ('D', 10, 1), ('E', 1, 1), ('E', 2, 1), ('E', 3, 1), ('E', 4, 1), ('E', 5, 1), ('E', 6, 1), ('E', 7, 1), ('E', 8, 1), ('E', 9, 1), ('E', 10, 1),
												('A', 1, 2), ('A', 2, 2), ('A', 3, 2), ('A', 4, 2), ('A', 5, 2), ('A', 6, 2), ('A', 7, 2), ('A', 8, 2), ('A', 9, 2), ('A', 10, 2), ('B', 1, 2), ('B', 2, 2), ('B', 3, 2), ('B', 4, 2), ('B', 5, 2), ('B', 6, 2), ('B', 7, 2), ('B', 8, 2), ('B', 9, 2), ('B', 10, 2), ('C', 1, 2), ('C', 2, 2), ('C', 3, 2), ('C', 4, 2), ('C', 5, 2), ('C', 6, 2), ('C', 7, 2), ('C', 8, 2), ('C', 9, 2), ('C', 10, 2),
												('A', 1, 3), ('A', 2, 3), ('A', 3, 3), ('A', 4, 3), ('A', 5, 3), ('A', 6, 3), ('A', 7, 3), ('A', 8, 3), ('A', 9, 3), ('A', 10, 3), ('B', 1, 3), ('B', 2, 3), ('B', 3, 3), ('B', 4, 3), ('B', 5, 3), ('B', 6, 3), ('B', 7, 3), ('B', 8, 3), ('B', 9, 3), ('B', 10, 3), ('C', 1, 3), ('C', 2, 3), ('C', 3, 3), ('C', 4, 3), ('C', 5, 3), ('C', 6, 3), ('C', 7, 3), ('C', 8, 3), ('C', 9, 3), ('C', 10, 3),
												('A', 1, 4), ('A', 2, 4), ('A', 3, 4), ('A', 4, 4), ('A', 5, 4), ('A', 6, 4), ('A', 7, 4), ('A', 8, 4), ('A', 9, 4), ('A', 10, 4), ('B', 1, 4), ('B', 2, 4), ('B', 3, 4), ('B', 4, 4), ('B', 5, 4), ('B', 6, 4), ('B', 7, 4), ('B', 8, 4), ('B', 9, 4), ('B', 10, 4), ('C', 1, 4), ('C', 2, 4), ('C', 3, 4), ('C', 4, 4), ('C', 5, 4), ('C', 6, 4), ('C', 7, 4), ('C', 8, 4), ('C', 9, 4), ('C', 10, 4), 
												('A', 1, 5), ('A', 2, 5), ('A', 3, 5), ('A', 4, 5), ('A', 5, 5), ('A', 6, 5), ('A', 7, 5), ('A', 8, 5), ('A', 9, 5), ('A', 10, 5), ('B', 1, 5), ('B', 2, 5), ('B', 3, 5), ('B', 4, 5), ('B', 5, 5), ('B', 6, 5), ('B', 7, 5), ('B', 8, 5), ('B', 9, 5), ('B', 10, 5), ('C', 1, 5), ('C', 2, 5), ('C', 3, 5), ('C', 4, 5), ('C', 5, 5), ('C', 6, 5), ('C', 7, 5), ('C', 8, 5), ('C', 9, 5), ('C', 10, 5);
*/


INSERT INTO Actor (FirstName, LastName, DOB)
VALUES ('John', 'Doe', '1975-08-10'),
 ('Jane', 'Smith', '1980-05-22'),
('Michael', 'Johnson', '1978-11-30'),
('Emily', 'Williams', '1982-04-15'),
('Robert', 'Brown', '1972-09-18'),
 ('Sarah', 'Lee', '1985-07-12'),
('David', 'Davis', '1976-12-05'),
('Jennifer', 'White', '1983-03-28'),
 ('Matthew', 'Anderson', '1979-06-20'),
 ('Laura', 'Martinez', '1981-10-08'),
 ('Christopher', 'Taylor', '1974-02-14'),
('Melissa', 'Thomas', '1987-09-25'),
('James', 'Harris', '1973-01-17'),
('Jessica', 'Clark', '1984-12-01'),
('William', 'Allen', '1986-08-09'),
 ('Amanda', 'Lewis', '1977-07-30'),
('Daniel', 'Scott', '1988-03-12'),
('Elizabeth', 'Green', '1989-06-28'),
 ('Christopher', 'Johnson', '1983-11-05'),
 ('Lauren', 'Turner', '1984-07-19'),
('Ryan', 'Martinez', '1990-04-02'),
('Stephanie', 'Harris', '1992-01-08'),
('Andrew', 'Lee', '1982-12-15'),
('Emma', 'Smith', '1980-05-15'),
('Olivia', 'Johnson', '1983-09-20'),
('Sophia', 'Williams', '1975-11-25'),
('Ava', 'Brown', '1988-04-10'),
 ('Isabella', 'Jones', '1990-07-05'),
('Mia', 'Miller', '1979-12-12'),
 ('Amelia', 'Davis', '1982-02-28'),
('Lily', 'Garcia', '1984-06-08'),
('Emily', 'Rodriguez', '1986-03-22'),
 ('Sofia', 'Martinez', '1976-08-18'),
 ('Avery', 'Hernandez', '1981-01-09'),
 ('Grace', 'Lopez', '1977-10-30'),
('Ella', 'Gonzalez', '1989-11-03'),
 ('Chloe', 'Perez', '1983-07-14'),
 ('Scarlett', 'Torres', '1991-02-25'),
 ('Aria', 'Flores', '1978-12-28'),
 ('Zoey', 'Rivera', '1980-09-17'),
 ('Penelope', 'Reyes', '1974-04-21'),
 ('Lillian', 'Long', '1986-06-06'),
('Addison', 'Lee', '1982-03-12'),
('Aubrey', 'Evans', '1990-08-23'),
 ('Stella', 'Baker', '1979-07-27'),
 ('Natalie', 'Morris', '1985-11-04'),
('Brooklyn', 'Ward', '1983-01-19'),
 ('Hannah', 'Cruz', '1977-12-06'),
('Leah', 'Simmons', '1988-05-09'),
 ('Zoe', 'Ortega', '1981-09-14'),
 ('Victoria', 'Barnes', '1976-04-08'),
('Nora', 'Harrison', '1989-03-01'),
 ('Riley', 'Fisher', '1984-08-12');

 INSERT INTO [Cast] VALUES
  (1, 1), (2, 1), (3, 1),
  (2, 2), (3, 2), (4, 2),
  (3, 3), (4, 3), (5, 3),
  (4, 4), (5, 4), (6, 4),
  (5, 5), (6, 5), (7, 5),
  (6, 6), (7, 6), (8, 6),
  (7, 7), (8, 7), (9, 7),
  (8, 8), (9, 8), (10, 8),
  (9, 9), (10, 9), (11, 9),
  (10, 10), (11, 10), (12, 10),
  (11, 11), (12, 11), (13, 11),
  (12, 12), (13, 12), (14, 12),
  (13, 13), (14, 13), (15, 13),
  (14, 14), (15, 14), (16, 14),
  (15, 15), (16, 15), (17, 15),
  (16, 16), (17, 16), (18, 16),
  (17, 17), (18, 17), (19, 17),
  (18, 18), (19, 18), (20, 18),
  (19, 19), (20, 19), (21, 19),
  (20, 20), (21, 20), (22, 20),
  (21, 21), (22, 21), (23, 21),
  (22, 22), (23, 22), (24, 22),
  (23, 23), (24, 23), (25, 23),
  (24, 24), (25, 24), (26, 24),
  (25, 25), (26, 25), (27, 25),
  (26, 26), (27, 26), (28, 26),
  (27, 27), (28, 27), (29, 27),
  (28, 28), (29, 28), (30, 28),
  (29, 29), (30, 29), (31, 29),
  (30, 30), (31, 30), (32, 30),
  (31, 31), (32, 31), (33, 31),
  (32, 32), (33, 32), (34, 32),
  (33, 33), (34, 33), (35, 33),
  (34, 34), (35, 34), (36, 34),
  (35, 35), (36, 35), (37, 35),
  (36, 36), (37, 36), (38, 36),
  (37, 37), (38, 37), (39, 37),
  (38, 38), (39, 38), (40, 38),
  (39, 39), (40, 39), (41, 39),
  (40, 40), (41, 40), (42, 40),
  (41, 41), (42, 41), (43, 41),
  (42, 42), (43, 42), (44, 42),
  (43, 43), (44, 43), (45, 43),
  (44, 44), (45, 44), (46, 44),
  (45, 45), (46, 45), (47, 45),
  (46, 46), (47, 46), (48, 46),
  (47, 47), (48, 47), (49, 47),
  (48, 48), (49, 48), (50, 48),
  (49, 49), (50, 49), (51, 49),
  (50, 50), (51, 50), (52, 50),
  (51, 51), (52, 51), (53, 51),
  (52, 52), (53, 52), (1, 52),
  (53, 53), (1, 53), (2, 53);

  INSERT INTO Genre (GenreName) VALUES
    ('Action'),
    ('Adventure'),
    ('Animation'),
    ('Comedy'),
    ('Crime'),
    ('Drama'),
    ('Fantasy'),
    ('Horror'),
    ('Mystery'),
    ('Romance'),
    ('Science Fiction'),
    ('Thriller'),
    ('Western'),
    ('Documentary'),
    ('Family'),
    ('History'),
    ('Music'),
    ('War'),
    ('Biography'),
    ('Sport'),
    ('Musical'),
    ('Film-Noir'),
    ('Sci-Fi'),
    ('Superhero');


  INSERT INTO Classify (MovieID, GenreID) VALUES
    (1, 1), -- Action
    (1, 3), -- Animation
    (1, 13), -- Family
    (2, 2), -- Adventure
    (2, 3), -- Animation
    (2, 10), -- Romance
    (3, 1), -- Action
    (3, 7), -- Fantasy
    (3, 21), -- Sci-Fi
    (4, 5), -- Crime
    (4, 12), -- Thriller
    (4, 19), -- Biography
    (5, 7), -- Fantasy
    (5, 12), -- Thriller
    (5, 24), -- Superhero
    (6, 9), -- Mystery
    (6, 11), -- Romance
    (6, 17), -- Music
    (7, 5), -- Crime
    (7, 12), -- Thriller
    (7, 14), -- Documentary
    (8, 1), -- Action
    (8, 6), -- Drama
    (8, 20), -- Sport
    (9, 6), -- Drama
    (9, 11), -- Romance
    (9, 22), -- Musical
    (10, 2), -- Adventure
    (10, 6), -- Drama
    (10, 13), -- Family
    (11, 1), -- Action
    (11, 6), -- Drama
    (11, 12), -- Thriller
    (12, 1), -- Action
    (12, 5), -- Crime
    (12, 7), -- Fantasy
    (13, 3), -- Animation
    (13, 9), -- Mystery
    (13, 16), -- Horror
    (14, 6), -- Drama
    (14, 10), -- Romance
    (14, 11), -- Music
    (15, 1), -- Action
    (15, 14), -- Documentary
    (15, 22), -- Musical
    (16, 2), -- Adventure
    (16, 6), -- Drama
    (16, 15), -- History
    (17, 1), -- Action
    (17, 7), -- Fantasy
    (17, 21), -- Sci-Fi
    (18, 5), -- Crime
    (18, 12), -- Thriller
    (18, 14), -- Documentary
    (19, 1), -- Action
    (19, 7), -- Fantasy
    (19, 22), -- Musical
    (20, 5), -- Crime
    (20, 12), -- Thriller
    (20, 24), -- Superhero
    (21, 3), -- Animation
    (21, 7), -- Fantasy
    (21, 13), -- Family
    (22, 1), -- Action
    (22, 6), -- Drama
    (22, 15), -- History
    (23, 1), -- Action
    (23, 5), -- Crime
    (23, 10), -- Romance
    (24, 2), -- Adventure
    (24, 6), -- Drama
    (24, 22), -- Musical
    (25, 1), -- Action
    (25, 6), -- Drama
    (25, 14), -- Documentary
    (26, 7), -- Fantasy
    (26, 11), -- Romance
    (26, 13), -- Family
    (27, 1), -- Action
    (27, 12), -- Thriller
    (27, 21), -- Sci-Fi
    (28, 1), -- Action
    (28, 6), -- Drama
    (28, 10), -- Romance
    (29, 2), -- Adventure
    (29, 6), -- Drama
    (29, 13), -- Family
    (30, 5), -- Crime
    (30, 12), -- Thriller
    (30, 24), -- Superhero
    (31, 6), -- Drama
    (31, 11), -- Romance
    (31, 22), -- Musical
    (32, 3), -- Animation
    (32, 9), -- Mystery
    (32, 17), -- Music
    (33, 5), -- Crime
    (33, 12), -- Thriller
    (33, 19), -- Biography
    (34, 7), -- Fantasy
    (34, 12), -- Thriller
    (34, 24), -- Superhero
    (35, 1), -- Action
    (35, 6), -- Drama
    (35, 10), -- Romance
    (36, 2), -- Adventure
    (36, 6), -- Drama
    (36, 21), -- Sci-Fi
    (37, 1), -- Action
    (37, 5), -- Crime
    (37, 10), -- Romance
    (38, 3), -- Animation
    (38, 7), -- Fantasy
    (38, 13), -- Family
    (39, 6), -- Drama
    (39, 12), -- Thriller
    (39, 19), -- Biography
    (40, 7), -- Fantasy
    (40, 11), -- Romance
    (40, 13), -- Family
    (41, 1), -- Action
    (41, 6), -- Drama
    (41, 14), -- Documentary
    (42, 7), -- Fantasy
    (42, 12), -- Thriller
    (42, 24), -- Superher
    (43, 3), -- Animation
    (43, 9), -- Mystery
    (43, 16), -- Horror
    (44, 5), -- Crime
    (44, 12), -- Thriller
    (44, 19), -- Biography
    (45, 7), -- Fantasy
    (45, 11), -- Romance
    (45, 13), -- Family
    (46, 1), -- Action
    (46, 6), -- Drama
    (46, 15), -- History
    (47, 1), -- Action
    (47, 5), -- Crime
    (47, 10), -- Romance
    (48, 2), -- Adventure
    (48, 6), -- Drama
    (48, 13), -- Family
    (49, 7), -- Fantasy
    (49, 12), -- Thriller
    (49, 24), -- Superhero
    (50, 1), -- Action
    (50, 6), -- Drama
    (50, 10), -- Romance
    (51, 3), -- Animation
    (51, 7), -- Fantasy
    (51, 13), -- Family
    (52, 6), -- Drama
    (52, 12), -- Thriller
    (52, 19), -- Biography
    (53, 3), -- Animation
    (53, 7), -- Fantasy
    (53, 17); -- Music

insert into Room(RoomName, NumRows, NumCols, BranchID) values
('GC-01',20,35,1),
('GC-02',5,4,1),
('GC-03', 35, 35, 1),
('GC-04', 25, 35, 1),
('GC-05', 25, 25, 1),
('GV-01', 35, 35, 2),
('GV-02', 25, 35, 2),
('GV-03', 25, 25, 2),
('GV-04', 35, 35, 2),
('GV-05', 25, 35, 2)

insert into SeatType values
('Normal'),
('VIP');

insert into TicketPrice values
(1, 90000, '2023-06-06'),
(2, 150000, '2023-06-06'),
(1, 100000, '2023-06-16');

insert into Seat values
(0,0,0,1,1,1),
(0,0,0,2,1,1),
(1,0,4,1,1,1),
(1,0,4,2,1,1),
(2,0,8,1,1,1),
(2,0,8,2,1,1),

(0,0,0,1,2,2),
(0,0,0,2,2,2),
(1,0,1,1,2,2),
(1,0,1,2,2,2),
(2,0,2,1,1,2),
(2,0,2,2,1,2),
(3,0,3,1,1,2),
(3,0,3,2,1,2),
(4,0,4,1,1,2),
(4,0,4,2,1,2)

insert into [FormatType] values
('2D'),
('3D'),
('IMAX'),
('4DX'),
('SCREENX');

/*
INSERT INTO Show (MovieID, RoomID, StartTime) VALUES(1, 3, dateadd(hour,1,getdate())), (1, 2, dateadd(hour,4,getdate())), (1, 1, dateadd(hour,7,getdate())), (1, 5, dateadd(hour,10,getdate())), (1, 4, dateadd(hour,13,getdate())), 
													(1, 3, dateadd(hour,25,getdate())), (1, 2, dateadd(hour,28,getdate())), (1, 1, dateadd(hour,31,getdate())), (1, 5, dateadd(hour,34,getdate())), (1, 4, dateadd(hour,37,getdate())), 
													(1, 3, dateadd(hour,49,getdate())), (1, 2, dateadd(hour,52,getdate())), (1, 1, dateadd(hour,55,getdate())), (1, 5, dateadd(hour,58,getdate())), (1, 4, dateadd(hour,61,getdate())), 
													(1, 3, dateadd(hour,73,getdate())), (1, 2, dateadd(hour,76,getdate())), (1, 1, dateadd(hour,79,getdate())), (1, 5, dateadd(hour,82,getdate())), (1, 4, dateadd(hour,85,getdate())), 
													(1, 3, dateadd(hour,97,getdate())), (1, 2, dateadd(hour,100,getdate())), (1, 1, dateadd(hour,103,getdate())), (1, 5, dateadd(hour,106,getdate())), (1, 4, dateadd(hour,109,getdate())),
													(2, 4, dateadd(hour,1,getdate())), (2, 3, dateadd(hour,4,getdate())), (2, 2, dateadd(hour,7,getdate())), (2, 1, dateadd(hour,10,getdate())), (2, 5, dateadd(hour,13,getdate())), 
													(2, 4, dateadd(hour,25,getdate())), (2, 3, dateadd(hour,28,getdate())), (2, 2, dateadd(hour,31,getdate())), (2, 1, dateadd(hour,34,getdate())), (2, 5, dateadd(hour,37,getdate())), 
													(2, 4, dateadd(hour,49,getdate())), (2, 3, dateadd(hour,52,getdate())), (2, 2, dateadd(hour,55,getdate())), (2, 1, dateadd(hour,58,getdate())), (2, 5, dateadd(hour,61,getdate())), 
													(2, 4, dateadd(hour,73,getdate())), (2, 3, dateadd(hour,76,getdate())), (2, 2, dateadd(hour,79,getdate())), (2, 1, dateadd(hour,82,getdate())), (2, 5, dateadd(hour,85,getdate())), 
													(2, 4, dateadd(hour,97,getdate())), (2, 3, dateadd(hour,100,getdate())), (2, 2, dateadd(hour,103,getdate())), (2, 1, dateadd(hour,106,getdate())), (2, 5, dateadd(hour,109,getdate())),
													(3, 5, dateadd(hour,1,getdate())), (3, 4, dateadd(hour,4,getdate())), (3, 3, dateadd(hour,7,getdate())), (3, 2, dateadd(hour,10,getdate())), (3, 1, dateadd(hour,13,getdate())), 
													(3, 5, dateadd(hour,25,getdate())), (3, 4, dateadd(hour,28,getdate())), (3, 3, dateadd(hour,31,getdate())), (3, 2, dateadd(hour,34,getdate())), (3, 1, dateadd(hour,37,getdate())), 
													(3, 5, dateadd(hour,49,getdate())), (3, 4, dateadd(hour,52,getdate())), (3, 3, dateadd(hour,55,getdate())), (3, 2, dateadd(hour,58,getdate())), (3, 1, dateadd(hour,61,getdate())), 
													(3, 5, dateadd(hour,73,getdate())), (3, 4, dateadd(hour,76,getdate())), (3, 3, dateadd(hour,79,getdate())), (3, 2, dateadd(hour,82,getdate())), (3, 1, dateadd(hour,85,getdate())), 
													(3, 5, dateadd(hour,97,getdate())), (3, 4, dateadd(hour,100,getdate())), (3, 3, dateadd(hour,103,getdate())), (3, 2, dateadd(hour,106,getdate())), (3, 1, dateadd(hour,109,getdate())), 
													(4, 1, dateadd(hour,25,getdate())), (4, 5, dateadd(hour,28,getdate())), (4, 4, dateadd(hour,31,getdate())), (4, 3, dateadd(hour,34,getdate())), (4, 2, dateadd(hour,37,getdate())), 
													(4, 1, dateadd(hour,49,getdate())), (4, 5, dateadd(hour,52,getdate())), (4, 4, dateadd(hour,55,getdate())), (4, 3, dateadd(hour,58,getdate())), (4, 2, dateadd(hour,61,getdate())), 
													(4, 1, dateadd(hour,73,getdate())), (4, 5, dateadd(hour,76,getdate())), (4, 4, dateadd(hour,79,getdate())), (4, 3, dateadd(hour,82,getdate())), (4, 2, dateadd(hour,85,getdate())), 
													(4, 1, dateadd(hour,97,getdate())), (4, 5, dateadd(hour,100,getdate())), (4, 4, dateadd(hour,103,getdate())), (4, 3, dateadd(hour,106,getdate())), (4, 2, dateadd(hour,109,getdate())),
													(5, 2, dateadd(hour,25,getdate())), (5, 1, dateadd(hour,28,getdate())), (5, 5, dateadd(hour,31,getdate())), (5, 4, dateadd(hour,34,getdate())), (5, 3, dateadd(hour,37,getdate())), 
													(5, 2, dateadd(hour,49,getdate())), (5, 1, dateadd(hour,52,getdate())), (5, 5, dateadd(hour,55,getdate())), (5, 4, dateadd(hour,58,getdate())), (5, 3, dateadd(hour,61,getdate())), 
													(5, 2, dateadd(hour,73,getdate())), (5, 1, dateadd(hour,76,getdate())), (5, 5, dateadd(hour,79,getdate())), (5, 4, dateadd(hour,82,getdate())), (5, 3, dateadd(hour,85,getdate())), 
													(5, 2, dateadd(hour,97,getdate())), (5, 1, dateadd(hour,100,getdate())), (5, 5, dateadd(hour,103,getdate())), (5, 4, dateadd(hour,106,getdate())), (5, 3, dateadd(hour,109,getdate()));

INSERT INTO [Format] values
(1, 1), (1, 2)
*/
/*
insert into VerificationType values
('pwd_forgot'),
('acc_verify')
*/

insert into [Gender] values 
(N'Nam'),
(N'Nữ'),
(N'Khác'),
(N'Không muốn trả lời');

insert into [ManagerRole] values 
('HeadManager'),
('CinemaManager');

insert into [Customer] values 
('khanh123','123', N'Khánh',N'Đỗ Duy',1,'hungthinh@gmail.com','8 January 2001',N'Hà Nội','09738888834',1),
('dungbt2','123', N'Dũng',N'Bùi Tiến',1,'dungvanthinhpz1@gmail.com','24 February 2002',N'Hồ Chí Minh','09738848332',1),
('manhpd','123', N'Phạm',N'Đức Mạnh',1,'sapocxdcwqeqweqwe@gmail.com','21 June 2002',N'Hải Phòng','0705897004', 1),
('anlt','123', N'Lê',N'Thành An',1,'f7fjsgre4@gmail.com','13 March 2003',N'Quảng Ninh','0385779948', 1),
('dungbt','123', N'Bùi',N'Tiến Dũng',1,'genshinzxc4@gmail.com','13 March 2003',N'Quảng Ninh','0385771945', 1),
('luandt','123', N'Dương',N'Thành Luân',1,'anhvaem12311@gmail.com','13 March 2003',N'Quảng Ninh','0385729945', 1);
/*
insert into [Customer] values 
('sa','12345678', 'Admin','Admin',1,'Admin@AdminMail.com','1 January 1970','System','00000000000',1)

*/
insert into [ManagerRole] values 
('HeadManager'),
('CinemaManager');

insert into TimeSlot values
('2023-06-10 09:00:00', '2023-06-10 12:00:00',2),
('2023-06-10 15:00:00', '2023-06-10 18:00:00',2);

insert into [Manager] values 
('luongnp1','123', N'Nguyễn',N'Phú Lương',1,'npl.working@gmail.com','13 March 2003',N'Quảng Ninh','0385779946',1, 1, 1),
('luongbao1','123', N'Nguyễn',N'Phú Lương',1,'npld.working@gmail.com','13 March 2003',N'Quảng Ninh','0385779942',2, 1, 1),
('manhpd1','123', N'Phạm',N'Đức Mạnh',1,'sapocxdcwqeqweqwe@gmail.com','21 June 2002',N'Hải Phòng','0705897004',2, 1, 1),
('anlt1','123', N'Lê',N'Thành An',1,'f7fjsgre4@gmail.com','13 March 2003',N'Quảng Ninh','0385779943',2, 1, 1),
('dungbt1','123', N'Bùi',N'Tiến Dũng',1,'genshinzxc4@gmail.com','13 March 2003',N'Quảng Ninh','0385779941',2, 1, 1),
('luandt','123', N'Dương',N'Thành Luân',1,'anhvaem12311@gmail.com','13 March 2003',N'Quảng Ninh','0385779948',2, 1, 1);

/*
insert into Show ([Status], MovieID, TimeSlotID) values
(0,5,1),
(0,3,2)
*/