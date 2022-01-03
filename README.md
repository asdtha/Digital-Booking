Digital Booking is an accommodation booking website, where you can find hotels, hostels, lodges and bed & breakfasts. The site allows three navigation flows: non-logged in user, logged in user and admin user; to make a reservation it is necessary to register and/or log in. At the time of registration an email is sent to the user to confirm the registration, you can also recover your password, which is also stored encrypted in the database.
We implemented filters to search by type of accommodation, city and/or date range. You can see the details of a product where an image gallery, available dates, location on the map and policies are shown. There is a section for favorites and bookings already made. The site is responsive and the UX/UI was taken into account.
Additionally we used some AWS services; we dockerized both the front-end, back-end, and ran the containers in an ec2 instance, stored the data in an RDS instance and the images in an S3 bucket.

We worked in an Agile environment under Scrum methodology and some of the technologies, languages, libraries and frameworks we worked with were:
-Front-end: HTML, CSS, JavaScript, React Library
-Back-end: Java, Spring Framework, Spring Boot, Spring Boot Security, Spring Boot Mail, Spring Boot JPA, Spring Doc, Hibernate ORM, jwt, log4j, maven
-Database: Mysql
-Testing: JUnit, Jest, Postman, React Testing Library and manual Testing.
-Infrastructure: Terraform, Docker, AWS (resource provider: Ec2, RDS mysql, S3, Route 53)
-Version control system: Git
-Remote hosting service for Git repository: Gitlab
