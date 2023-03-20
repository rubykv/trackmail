# trackmail
Java app to send and track email

# Installation
This app is built on Spring Boot 3.0.4 and Gradle-Kotlin and is generated from https://start.spring.io/

1. Install OpenJDK 17
2. Install Redis (Alternatively use a Redis Client, example - Red for Mac)

# How to Run the App
1. Clone this repository
2. Set up your Gmail password to be used by this app here - https://myaccount.google.com/apppasswords
3. Store your google creds in Redis using this command -> HMSET EmailAccount id “admin” emailId "your-email-id" appPwd "your-app-password”
4. Build and run the spring boot app
5. Use this curl to send email
`curl --location 'http://localhost:8080/email/send' \
--header 'Content-Type: application/json' \
--data-raw '{"subject":"test mail","content":"this is a test email","toAddress":"xx@yy.com"}`

6. Tracker API is called from email client (localhost calls are only made from email clients like Outlook and Mail on Mac, it will not be called from browsers)



  

