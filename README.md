Smart URL

Smart URL is a full-stack URL shortening application built using Spring Boot and vanilla HTML, CSS, and JavaScript.
It follows a client–server architecture and provides real-time analytics for shortened links.

The project focuses on clean backend design, efficient database usage, and practical frontend–backend integration without relying on third-party UI frameworks.

Features
Create short URLs from long URLs
Optional custom alias support
URL expiry handling
Redirect short URLs to original destinations
Click analytics tracking
Analytics dashboard with usage insights
Clean REST API design
Same-origin frontend and backend deployment (no CORS issues)

Tech Stack
Backend
Java
Spring Boot
Spring Web MVC
Spring Data JPA
MySQL
Hibernate

Frontend
HTML
CSS
JavaScript (Fetch API)

Application Architecture
Client–Server architecture
RESTful APIs for URL creation and analytics
Frontend served via Spring Boot static resources
Database-driven persistence layer
Service layer for business logic isolation
Repository layer with custom JPQL queries for analytics

Pages
URL Shortening Page

This page allows users to enter a long URL, optionally provide a custom alias and expiry time, and generate a shortened URL.

Image:
Example:

url.png

Analytics Dashboard

The analytics dashboard allows users to enter a short URL and view detailed insights such as total clicks, browser usage, operating system distribution, and last accessed time.

Image:
Example:

Analytics.png

API Endpoints
Create Short URL
POST /api/urls


Request Parameters:
originalurl (required)
customAlias (optional)
expiry (optional)

Redirect Short URL
GET /{shortCode}


Redirects the user to the original URL and records click analytics.

Fetch Analytics
GET /api/analytics/{shortCode}
Returns usage statistics for the given short URL.

Database Design
url_mapping table for storing URL data
click_analytics table for storing click events
Optimized indexes for fast lookups
No unnecessary joins to improve performance

How to Run the Project
Clone the repository
Configure MySQL database credentials in application.properties
Run the Spring Boot application


Public analytics sharing

Deployment on cloud platform

