# Notas do Curso - JUnit 4, Mockito e TDD

## Princípio F.I.R.S.T
 - Fast: O teste deve ser rápido;
 - Independent: O teste deve ser independente;
 - Repeatable: O R vem de repetitível, a qualquer momento que for executado deve dar o mesmo resultado;
 - Self-Verifying: O próprio teste deve saber quando o resultado foi com sucesso, falha ou erro;
 - Timely: Este vem de oportuno. OU seja um teste deve ser criado no momento oportúnuo;

## Frameworks XUnit
 Estes frameworks tem este nome por conta no framework SUnit desenvolvido na linguagem de programação Smalltalk, criado por Kent Beck, 1998.
 Todos estes frameworks tentam seguir um mesmo componente principal de sua arquitetura, e são compostos de alguns componentes:
 - TestRunner: Responsável por executar os testes e coletar os resultados;
 - TestFixture (TestContext): Que são as precondições necessárias aos testes;
 - TestSuites: Permite que sejam agrupados um conjunto de testes que devem ser executados em conjunto;
 - TestResultFormatter: Quem formata a saída dos testes;
 - Assertions: São verificadores de estado do teste;

O JUnit também pode ser utilizado para testes de integração e testes funcionais como ferramenta de padronização.

## Metáfora da Janela de Vidro
Ela foi introduzida em 1982 na área de criminologia, e tenta explicar uma característica do comportamento humano. Considere um edifício
com algumas janelas quebradas, se as janelas não forem reparadas, a tendencia é que vândalos quebrem mais janelas.
Portanto, é sugerido que sempre seja deixado os testes com a barra verde, pois assim, evitasse que mais testes com barra vermelha
se acumulem.

## Aula 05 - Organização das classes
Colocar as classes de teste na mesma estrutura de pacote das classes testadas. Dessa forma, somente os atributos e métodos
privados não podem ser acessados mas os definidos com os outros modificadores são visíveis.

## Aula 06 - Assertivas
As assertivas são uma característica dos frameworks XUnit, ela está diretamente ligada a uma expressão lógica. Caso a
asserção falhe, o teste também falha.

## Aula 07 - AssertThat
O método **assertThat** da classe **Assert**, é usado para tornar as verificações de determinado caso de teste
mais flúidas, pois usa métodos encadeados que são conhecidos como matchers. Esse encadeamento
de métodos com nomes semânticos é conhecido como **Fluent Interface**, que foi mencionado primeiramente
por **Martin Fowler**, em 2005. O Junit tem a dependência para o **Hamcrest** que trás consigo diversos matchers úteis.
Vale notar que a ordem dos argumentos agora está invertida. No caso do método **assertThat**, o primeiro argumento
se refere ao valor atual, e o segundo argumento recebe um matcher com o valor esperado.
Para tornar a leitura da assertiva mais legível, basta adicionar imports estáticos dos métodos e matchers.

## Aula 08 - Divisão de um teste
Cada teste deve ser isolado, tendo na maioria das vezes somente uma única assertiva. Dessa forma, como os testes estariam
fazendo uma única verificação lógica, seria mais fácil fazer o rastreamento dos erros quando necessário.
Em caso de falha na asserção, o teste falha, conforme o esperado. Mesmo essa sendo uma forma de fazer a divisão de um teste
também há a outra maneira que é com mais asserções no mesmo teste. Essa situação é aceitável quando o cenário e ação
do teste não mudam com muita frequência, tornando assim o gerenciamento macro do teste e mais de uma asserção por teste.
Existe uma maneira de minimizar a abordagem macro dos testes que é coletando todos os erros de uma única vez, para posterior
análise em caso de falha em várias asserções de um teste. Essa funcionalidade é conhecida como **Rule**. Ela é apresentada
junto deste commit no teste **testeLocacao** da classe de teste **LocacaoServiceTest**. Essa duas soluções ou abordagens
são as mais usadas, e essa segunda, usando uma abordagem macro dos testes é preferível pelo autor do
curso, **Wagner Costa Aquino**.

## Aula 09 - Exceções - Parte 1
As falhas são caracterizadas por condições que são avaliadas, nas asserções, e
não são dadas como verdadeiras, fazendo com que o teste falhe.
Já os erros são ocasionados por falhas na execução do fluxo correto do teste e, portanto,
impedindo que ele seja concluído com sucesso. Ou seja, quando ocorre alguma exceção. 
Uma outra possibilidade é capturar a exceção lançada e usar o método **fail()**
da classe **Assert** para quando a exceção ocorrer, e dessa forma, ser capturada
no bloco catch, o método **fail()** ser invocado e então informar para o JUnit
que o teste deve falhar. Mas dessa forma, a questão da rastreabilidade do teste
é prejudicada. Porque dessa maneira, perdesse o ponto onde a exceção foi lançada.
Portanto, fica a dica, se o teste não está esperando o lançamento de exceção alguma,
deixe que o JUnit gerencie a exceção lançada, pois assim, caso ocorra um erro a
pilha erá informada.

Agora, caso o teste esteja sendo projetado para esperar o lançamento de uma exceção
, ou seja, o teste deve passar caso seja lançada a exceção, então, uma das possibi-
lidades é declarar na anotação **@Test** a exceção esperada com o metadado
**expected**.
Exemplo: **@Test(expected = Exception.class)**, neste exemplo está sendo esperado
que seja lançada uma exceção do tipo **Exception**. Essa abordagem é considerada
elegante, por sua simplicidade.

Uma segunda abordagem é fazer uso do bloco try-catch para capturar a exceção e
validar a mensagem de erro da mesma. Para informar ao JUnit que o teste deve
falhar quando não ocorrer a exceção esperada, usamos o método **fail()** da classe
**Assert**.

A terceira abordagem é fazer uso de uma **@Rule**. Essa rule é declarada como uma
variável de instância, do tipo **ExpectedException**, e inicializada com o método estático
**ExpectedException.none**.

As três abordagens mencionadas foram implementadas na classe **LocacaoServiceTest**, neste
commit. As mesmas serão discutidas na parte 2 deste assunto.

## Aula 10 - Exceções - Parte 2
Essa aula é composta de uma discussão sobre as maneiras de lidar com exceções em testes unitários.
 1. Elegante: Mesmo sendo muito prática, ela é superficial. Mas por quê?
Bem, porquê o teste passa quando uma exceção do tipo informado for lançada, mas não há como garantir que
foi lançada pelo motivo adequado, ou seja, pela razão correta. Se houver mais de um lançamento de exceção
para a exceção **Exception** (definida neste exemplo como a exceção de validação de estoque vazio), não
será possível saber o motivo do lançamento; podendo haver multiplas causas. Isso acontece porque a forma
elegante não consegue verificar a mensagem da exceção. Para corrigir essa situação, foi criada a exceção
**FilmeSemEstoqueException**. Que deve ser considerada no momento de verificar se o teste deve passar ou
não.
</br>
**Nota**: Nessa aula foram adicionadas mais duas validações no método de alugar filme.

 2. Robusta: Essa abordagem é considerada robusta por que possibiita além de tratar a exception, fazer
a verificação da mensagem de causa na exceção. Ambas as formas, tanto, a forma Robusta quanto a forma
Nova tornam possível fazer a verificação da mensagem de causa da exceção. Vale ressaltar que esta forma
é a única que faz com que o código possa se executado além da exceção, pois foi feito o tratamento e verificação
posterior com a **Assert**.

 3. Já esta forma, avisa ao JUnit que podem ser lançadas determinadas exceções e que caso alguma das exceções
 que foram reportadas forem lançadas na execução do teste, e além da mensagem ter que estar contida na exceção
 aí sim que deve ser considerado como uma execução normal.

## Aula 11 - Before e After
O framework JUnit com o objetivo de tornar possível a execução de um método que configure alguma característica
em um teste, disponibiliza as anotações **@Before** e **@After**. Essas anotações quando aplicadas a um método
`public void` fazem com que o método anotado seja invocado antes e depois, respectivamente, de cada método
anotado com **@Test**.

O framework também disponibiliza um outro par de anotações com o objetivo de executar determinada tarefa apenas uma única vez
para toda uma classe de teste. Dessa forma, um método anotado com **@BeforeClass** (que deve ser um método `public static void`)
é invocado antes da execução dos testes da classe, ou seja, antes da instânciação do objeto. De maneira semelhante, quando um método
anotado com **@AfterClass**, que também deve ter as característica de um método `public static void`, o mesmo é invocado após
todos os testes da classe serem executados.

## Aula 12 - Ordem na execução dos testes
Quanto a ordem de execução dos casos de teste de uma classe de teste, o JUnit não garante que os testes são executados na
ordem em que foram declarados. Mas essa é uma das características que uma classe de teste deve seguir, ou seja, os testes devem ser
independentes entre si. Para testar essa característica, foi criado a classe **OrdemTest** com o objetivo de criar dois casos de teste
que tem uma dependencia entre si. O caso de teste **inicia()** incremeta um contador, e o caso de teste **verifica()** faz uma asserção
quanto ao valor que está na variável contador. Caso o teste **verifica()** seja executado antes que o teste **inicia()**, o teste
não passa, porque o valor da variável contador está incorreto.

Uma forma de assegurar que o teste passe é, removendo a anotação **@Test** dos dois casos de teste; criando um novo caso de teste, neste
caso chamado **testGeral()**, que faz a invocação dos dois métodos anteriores em uma ordem predefinida, visando estabelecer uma ordem
na execução da invocação dos métodos. Vale ressaltar que dessa forma, é prejudicado a rastreabilidade dos testes.

Outra forma de assegurar que os casos de teste executem em uma ordem específica, é usando a anotação **@FixMethodOrdem**, com o metadado
**MethodSorters.NAME_ASCENDING**. Dessa forma, deve-se notar que agora os testes devem ser declarados de forma léxica, usando até mesmo
de prefixos para facilitar a leitura, como por exemplo: `public void test1_*`, `public void test2_*` e etc;

## Aula 13 - Desafio - Alugando mais de um filme
A demanda do desafio é tornar possível alugar mais de um filme por vez, ou por locação. Dessa forma, deve ser possível alugar mais de um
filme por vez. Com isso os testes deixam de funcionar e portanto, deve ser feito a correção nos testes também.

O objetivo desse desafio foi perceber o quanto importante são os testes de software, pois caso não houvessem, ficaria mais difícil
saber se o código após a adição da nova funcionalidade está funcionando corretamente.
