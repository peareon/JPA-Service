## Overview
This project provides a reliable backend architecture for handling structured data. It seamlessly processes standard CRUD operations for various interconnected tables, while simultaneously maintaining system logs and managing profile records.

## Tech Stack
* **Java** - Core programming language
* **Spring Boot** - Application framework and API routing
* **MySQL** - Primary relational database management
* **H2** - In-memory database utilized for specific environments/testing
* **SLF4J** - Logging facade for systematic application tracking


## Installation
1. Clone this repository to your local machine.
2. Navigate into the root directory of the project.
3. Fetch the necessary dependencies using your standard build tool.
4. Update the application properties file with your specific database credentials and environment variables.

## Usage
Once the application is executed, the service will initialize on the default port (8080).

## Features
* **Relational Data Management:** Handles complex operations across multiple linked database tables.
* **Profile Handling:** Dedicated logic for profile lifecycle management.
* **System Logging:** Integrated SLF4J implementation to track events and states.
