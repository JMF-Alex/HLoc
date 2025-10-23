<a id="readme-top"></a>

<p align="center">
<a href="https://github.com/JMF-Alex/HLoc/graphs/contributors"><img src="https://img.shields.io/github/contributors/JMF-Alex/HLoc?style=for-the-badge" alt="Contributors"></a>
<a href="https://github.com/JMF-Alex/HLoc/network/members"><img src="https://img.shields.io/github/forks/JMF-Alex/HLoc?style=for-the-badge" alt="Forks"></a>
<a href="https://github.com/JMF-Alex/HLoc/stargazers"><img src="https://img.shields.io/github/stars/JMF-Alex/HLoc?style=for-the-badge" alt="Stargazers"></a>
<a href="https://github.com/JMF-Alex/HLoc/issues"><img src="https://img.shields.io/github/issues/JMF-Alex/HLoc?style=for-the-badge" alt="Issues"></a>
<a href="https://github.com/JMF-Alex/HLoc/blob/main/LICENSE"><img src="https://img.shields.io/github/license/JMF-Alex/HLoc?style=for-the-badge" alt="License"></a>
</p>

<p align="center">
<a href="https://www.oracle.com/java/"><img src="https://img.shields.io/badge/Java-21-blue?style=for-the-badge&logo=java&logoColor=white" alt="Java"></a>
<a href="https://maven.apache.org/"><img src="https://img.shields.io/badge/Maven-4.0.0-blue?style=for-the-badge&logo=Apache-Maven&logoColor=white" alt="Maven"></a>
<a href="https://img.shields.io/badge/License-MIT-green?style=for-the-badge"><img src="https://img.shields.io/badge/Size-2MB-orange?style=for-the-badge" alt="Size"></a>
</p>

## Table of Contents  
1. [About The Project](#about-the-project)  
2. [Built With](#built-with)  
3. [Getting Started](#getting-started)  
  * [Prerequisites](#prerequisites)  
  * [Installation](#installation)  
4. [Usage](#usage)  
5. [Roadmap](#roadmap)  
6. [Contributing](#contributing)  
7. [License](#license)  
8. [Acknowledgments](#acknowledgments)

## About the Project  
**HLoc** is a Java project that aims to replicate or work similarly to the **Wikiloc** platform for geolocation, routes, and outdoor activity tracking.  
The idea is to allow users to record, view, and manage routes, possibly with map and geolocation support.

### Features  
- Route management (create, view, edit)  
- Interface (or codebase) in Java  
- Modular design for future expansion  

## Built With  
This project was built using the following main technologies:  
- Java (version defined in `pom.xml`)  
- Maven as the build system  
- [Optional] External libraries for geolocation/maps if implemented later  
- Standard directory structure: `src/main/java`, `pom.xml`, etc. ([github.com](https://github.com/JMF-Alex/HLoc))

## Getting Started  

### Prerequisites  
- Java JDK installed (21+)  
- Maven installed (or use the included `mvnw`)  
- Git to clone the repository

### Installation  
1. Clone the repository:  
```bash
git clone https://github.com/JMF-Alex/HLoc.git
```  
2. Enter the project directory:  
```bash
cd HLoc
```  
3. (Opcional) Use the Maven wrapper:  
```bash
./mvnw clean install
```  
   or if you use globally installed Maven:  
```bash
mvn clean install
```  
4. Once compiled, you can run the application from your IDE or command line.

## Usage  
You can run the application with:  
```bash
java -jar target/HLoc‑1.0.0.jar
```  
From the interface you can:  
- View saved routes  
- Import previously saved routes  
- Edit or delete existing routes  

## Roadmap  
- [ ] Implement graphical user interface (GUI)  
- [ ] Add support for export to GPX/KML format  
- [ ] Integrate map API (e.g. OpenStreetMap)  
- [ ] Add user authentication and remote storage  

## Contributing  
Contributions are welcome.  
Please follow these steps:  
1. Fork the project  
2. Create your feature branch (`git checkout -b feature/MyFeature`)  
3. Commit your changes with a descriptive message (`git commit -m "Add some feature"`)  
4. Push to your branch (`git push origin feature/MyFeature`)  
5. Open a Pull Request  
Additionally, before developing a new feature, open an issue so we can discuss it.

## License  
Distributed under the MIT License. See the [`LICENSE`](LICENSE) file for more details.

## Acknowledgments  
- Original template by Othneil Drew: [Best‑README‑Template](https://github.com/othneildrew/Best-README-Template) ([github.com](https://github.com/othneildrew/Best-README-Template))
 
