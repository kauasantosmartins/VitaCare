
## VitaCare

VitaCare — sistema corporativo desktop em Java/JavaFX com banco de dados Microsoft SQL Server.

## Integrantes

- Kauã dos Santos
- Giovanni Durante

## Tema escolhido

Saúde e Bem-Estar.

## Descrição do problema resolvido

Clínicas, consultórios e profissionais de saúde costumam controlar pacientes, atendimentos e acompanhamento de rotina em papel ou em planilhas soltas. Isso gera dados duplicados, perda de histórico e dificuldade para saber quem foi atendido, por quem e como está a rotina de cada pessoa.

O sistema centraliza essas informações em um único lugar. Ele permite:

- cadastrar pacientes, com data de nascimento, objetivo de saúde (por exemplo, emagrecer ou melhorar o sono) e telefone;
- cadastrar os profissionais de saúde, com especialidade, registro profissional e e-mail;
- registrar consulta, ligando cada paciente ao profissional que o atendeu, com data e motivo;
- registrar os hábitos de bem-estar de cada paciente (caminhada, sono, hidratação etc.), com duração em minutos e data.

Com isso, o histórico de atendimentos e de hábitos de cada paciente fica organizado, pesquisável e consistente, porque consultas e hábitos só podem ser criados para pacientes e profissionais já cadastrados.

## Lista de entidades implementadas

| Entidade         | Atributos                                | Relacionamentos                                |
| ---------------- | ---------------------------------------- | ---------------------------------------------- |
| Paciente     | id, nome, nascimento, objetivo, telefone | pode possuir várias Consultas e vários Hábitos |
| Profissional | id, nome, especialidade, registro, email | atende várias Consultas                        |
| Consulta   | id, data, motivo, paciente, profissional | pertence a um Paciente e a um Profissional     |
| Hábito      | id, tipo, minutos, data, paciente        | pertence a um Paciente                         |

Cada entidade tem um CRUD completo (salvar/atualizar, pesquisar, excluir e listar), implementado nas quatro camadas:

| Camada               | Pacote     | Classes                                                                  
| Fronteira (boundary) | `boundary` | `MainBoundary`, `PacienteBoundary`, `ProfissionalBoundary`, `ConsultaBoundary`, `HabitoBoundary`, `Util` |
| Controle (control)   | `control`  | `PacienteControl`, `ProfissionalControl`, `ConsultaControl`, `HabitoControl`                             |
| Entidade (entity)    | `entity`   | `Paciente`, `Profissional`, `Consulta`, `Habito`                                                         |
| DAO                  | `dao`      | `DAO` (interface), `Conexao`, `PacienteDAO`, `ProfissionalDAO`, `ConsultaDAO`, `HabitoDAO`               |

O script de criação do banco está em `src/main/resources/saude.sql`.

## Instruções para execução
## Passo a passo

1. **Extraia** o arquivo `.zip` e abra a pasta `saude` no VS Code.
2. **Crie o banco de dados:** no SSMS, conectado à instância `SQLEXPRESS`, abra e execute o arquivo `src/main/resources/saude.sql`. Ele cria o banco `saude` e as tabelas `paciente`, `profissional`, `consulta` e `habito`, sem apagar dados já existentes.
3. **Confira a conexão:** em `src/main/java/dao/Conexao.java` está a URL do banco (`localhost`, porta TCP e banco `saude`). A porta da instância `SQLEXPRESS` é dinâmica e pode ser diferente na sua máquina. Se for o caso, ajuste o número da porta na constante `URL`.
4. Defina a autenticação:
   - **Autenticação do Windows (padrão):** não precisa configurar nada. A DLL `mssql-jdbc_auth-13.6.0.x64.dll` já está na pasta `libs` e é carregada automaticamente ao executar com o Gradle.
5.
   .\gradlew.bat clean build


   .\gradlew.bat run


## Divisão de responsabilidades por integrante

## Kauã dos Santos

- CRUD de Paciente completo: `Paciente`, `PacienteControl`, `PacienteBoundary` e `PacienteDAO`.
- CRUD de Consulta completo: `Consulta`, `ConsultaControl`, `ConsultaBoundary` e `ConsultaDAO`, incluindo as caixas de seleção de paciente e profissional.
- Tela principal com as abas e a atualização dos dados na troca de aba (`MainBoundary`).
- Script do banco de dados (`saude.sql`): criação das tabelas, chaves primárias e estrangeiras.
- Testes de integração entre os CRUDs de Paciente e Consulta.

## Giovanni Durante

- CRUD de Profissional completo: `Profissional`, `ProfissionalControl`, `ProfissionalBoundary` e `ProfissionalDAO`.
- CRUD de Hábito completo: `Habito`, `HabitoControl`, `HabitoBoundary` e `HabitoDAO`, incluindo a caixa de seleção de paciente.
- Camada de acesso a dados compartilhada: interface `DAO` e classe `Conexao` (conexão JDBC e conversão de datas).
- Configuração do projeto: `build.gradle`, Gradle Wrapper e scripts de execução.

## LINK DO VIDEO
https://youtu.be/6Oy9xPrulm8

