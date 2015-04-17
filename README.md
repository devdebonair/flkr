# FLKR - Recommendation System
------------------------------

## Goal

Implement a movie system that allows users to search and see the top movies, actors, and directors in the filming industry.

## Database

##### movie_actor
- movie_id      (integer)   Foreign Key -> movie
- actor_id      (string)    Primary Key
- actor_name    (string)
- ranking       (integer)

##### movie_director
- movie_id          (integer)   Foreign Key -> movie
- director_id       (string)    Primary Key
- director_name     (string)

##### movie_genre
- movie_id          (integer)   Foreign Key -> movie
- genre             (String)

##### movie
- id                            (integer)   Primary Key
- title_english                 (string)
- title_spanish                 (string)
- image_url_imdb                (string)
- year                          (integer)
- rt_critic_rating              (decimal)
- rt_critic_num_reviews         (integer)
- rt_critic_num_fresh           (integer)
- rt_critic_num_rotten          (integer)
- rt_critic_score               (decimal)
- rt_critic_top_rating          (decimal)
- rt_critic_top_num_reviews     (integer)
- rt_critic_top_num_fresh       (integer)
- rt_critic_top_num_rotten      (integer)
- rt_critic_top_score           (decimal)
- rt_audience_rating            (decimal)
- rt_audience_num_rating        (decimal)
- rt_audience_score             (decimal)
- image_url_rt                  (string)

##### movie_tag
- movie_id      (integer)   Foreign Key -> movie
- tag_id        (integer)   Foreign Key -> tag
- tag_weight    (integer)

##### tag
- id        	(integer)   Primary Key
- tag_name      (string)

#### actor
- id				(string)	Primary Key
- actor_name		(string)

#### director
- id				(string)	Primary Key
- director_name		(string)


# Code Base

###Readers
Readers read in the source data that is to be inserted into the database.

###Main
The Main class executes all of the readers and inserts data into the database.

###Database
The Database class connects to the database using the mysql jar file located in the lib/ folder. This class also contains the method that inserts the data into the database. Each query we will need corresponds to a method located in this class. There are 8 blank methods. Implement the method that will run the sql query you chose.

###FLKR_CLI
This will be the driver that will run the command line interface. This file is incomplete.