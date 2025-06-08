-- Drop the database if it already exists
DROP DATABASE IF EXISTS blog_system;

-- Create the blog database with utf8mb4 character set (supports emojis, etc.)
CREATE DATABASE blog_system DEFAULT CHARACTER SET utf8mb4;

-- Use the newly created database
USE blog_system;

-- Drop the user table if it exists
DROP TABLE IF EXISTS users;

-- Create the user table
CREATE TABLE users (
                           id INT PRIMARY KEY AUTO_INCREMENT,
                           username VARCHAR(100) UNIQUE,               -- Unique username
                           password VARCHAR(64) NOT NULL,              -- Hashed password
                           photo VARCHAR(500) DEFAULT '',              -- Optional profile photo URL
                           create_time DATETIME DEFAULT NOW(),         -- Time of account creation
                           update_time DATETIME DEFAULT NOW(),         -- Last update time
                           mail VARCHAR(100) UNIQUE,                   -- Unique email
                           nickname VARCHAR(100) UNIQUE,               -- Display name
                           introduction VARCHAR(500),                  -- User's bio
                           avatar_url VARCHAR(200) DEFAULT ''          -- Alternate field for avatar (may overlap with photo)
) DEFAULT CHARSET='utf8mb4';

-- Drop the blog table if it exists
DROP TABLE IF EXISTS posts;

-- Create the blog post table
CREATE TABLE posts (
                           id INT PRIMARY KEY AUTO_INCREMENT,
                           title VARCHAR(100) NOT NULL,                -- Blog post title
                           content TEXT NOT NULL,                      -- Main blog content
                           create_time DATETIME DEFAULT NOW(),         -- Publish time
                           update_time DATETIME DEFAULT NOW(),         -- Last edit time
                           user_id INT NOT NULL,                       -- Author ID (foreign key)
                           like_count INT DEFAULT 0,                   -- Number of likes
                           view_count INT DEFAULT 0                    -- Number of views
) DEFAULT CHARSET='utf8mb4';

-- Drop the comment table if it exists
DROP TABLE IF EXISTS comments;

-- Create the comment table
CREATE TABLE comments (
                              id INT PRIMARY KEY AUTO_INCREMENT,
                              parent_id INT,                              -- Parent comment ID (for nested replies)
                              user_id INT,                                -- Commenting user's ID
                              blog_id INT,                                -- Blog post ID being commented on
                              username VARCHAR(100),                      -- Redundant username (can be omitted if user_id used correctly)
                              comment VARCHAR(360),                       -- Comment text
                              create_time DATETIME DEFAULT NOW(),         -- Comment timestamp
                              like_count INT,                             -- Likes on the comment
                              replied_username VARCHAR(100)               -- Username being replied to
) DEFAULT CHARSET='utf8mb4';

-- Drop the blog_likes table if it exists
DROP TABLE IF EXISTS blog_likes;

-- Create the table to store blog like records
CREATE TABLE blog_likes (
                                id INT PRIMARY KEY AUTO_INCREMENT,
                                blog_id INT NOT NULL,                       -- Blog post that was liked
                                user_id INT NOT NULL,                       -- User who liked the blog
                                create_time DATETIME DEFAULT NOW(),         -- Like creation time
                                update_time DATETIME DEFAULT NOW()          -- Last updated (for future support like undo)
) DEFAULT CHARSET='utf8mb4';

-- Drop the follow table if it exists
DROP TABLE IF EXISTS follows;

-- Create the user follow relationship table
CREATE TABLE follows (
                             id INT PRIMARY KEY AUTO_INCREMENT,
                             user_id INT NOT NULL,                       -- Follower's user ID
                             followed_user_id INT NOT NULL,              -- Followed user's ID
                             create_time DATETIME DEFAULT NOW(),         -- When the follow started
                             update_time DATETIME DEFAULT NOW()          -- Last update (e.g., for unfollow logic)
) DEFAULT CHARSET='utf8mb4';
