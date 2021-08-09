drop table if exists product_reviews;
create table product_reviews (
    review_id bigint not null,
     user_name varchar(255),
     product_id varchar(255),
     review_comment varchar(255),
     review_score float,
     primary key (review_id)
 );

 drop table if exists user_details;
 create table user_details (user_name varchar(255) not null, email varchar(255), password varchar(255), primary key (user_name))