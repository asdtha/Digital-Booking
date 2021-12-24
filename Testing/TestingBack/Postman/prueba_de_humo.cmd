REM installar previamente Newman mediante comando npm install -g newman
REM ejecutar este cmd

REM booking happy
newman run https://www.getpostman.com/collections/3691ecbb688d8aa29add /k
Rem category exceptions /k
newman run https://www.getpostman.com/collections/66d1a08d9736fa259e06 /k
REM category happy
newman run https://www.getpostman.com/collections/b0d6e211f784aeda4dff /k
Rem City exceptions
newman run https://www.getpostman.com/collections/5e3cd6fc745d95313943
REM City Happy
newman run https://www.getpostman.com/collections/83bdd6cad2f97c4b3165
REM images exceptions
newman run https://www.getpostman.com/collections/0ba926df2952cc2715c3
REM images happy
newman run https://www.getpostman.com/collections/327f93f73f55ca271319
REM product exceptions
newman run https://www.getpostman.com/collections/a5fcc7ac420596fee43e
REM product API - Happy Path
newman run https://www.getpostman.com/collections/888ada487e0bdeb4bf42
