## Princípio F.I.R.S.T
 - Fast: O teste deve ser rápido;
 - Independent: O teste deve ser independente;
 - Repeatable: O R vem de repetitível, a qualquer momento que for executado deve dar o mesmo resultado;
 - Self-Verifying: O próprio teste deve saber quando o resultado foi com sucesso, falha ou erro;
 - Timely: Este vem de oportuno. OU seja um teste deve ser criado no momento oportúnuo;

## Frameworks XUnit
 Estes frameworks tem este nome por conta no framework SUnit desenvolvido na linguagem de programação Smalltalk, criado por Kent Beck, 1998. Todos estes frameworks tentam seguir um mesmo componente principal de sua arquitetura, e são compostos de alguns componentes:
 - TestRunner: Responsável por executar os testes e coletar os resultados;
 - TestFixture (TestContext): Que são as precondições necessárias aos testes;
 - TestSuites: Permite que sejam agrupados um conjunto de testes que devem ser executados em conjunto;
 - TestResultFormatter: Quem formata a saída dos testes;
 - Assertions: São verificadores de estado do teste;

O JUnit também pode ser utilizado para testes de integração e testes funcionais como ferramenta de padronização.

## Metáfora da Janela de Vidro
Ela foi introduzida em 1982 na área de criminologia, e tenta explicar uma característica do comportamento humano. Considere um edifício com algumas janelas quebradas, se as janelas não forem reparadas, a tendencia é que vândalos quebrem mais janelas.
Portanto, é sugerido que sempre seja deixado os testes com a barra verde, pois assim, evitasse que mais testes com barra vermelha se acumulem.