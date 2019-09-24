package jogodecartaspifpaf;

import java.util.Scanner;

public class JogoDeCartasPifPaf {

    private final Scanner entrada = new Scanner(System.in);
    private final Baralho BARALHO;
    private Jogador[] jogadores;
    private Carta[] cartasDescartadas;
    private int indiceDescarte = 0;
    public int contadorVitoria = 1;
    public int vitoria = 0;

    public JogoDeCartasPifPaf() {
        BARALHO = new Baralho();
        BARALHO.mostrarBaralho();
        BARALHO.embaralhar();
        BARALHO.setUltimaCarta(BARALHO.getCARTAS().length - 1);
    } //Preparando o inicio do jogo, inicia carta, baralho e embaralha

    public void iniciarJogo() {
        jogadores = new Jogador[2];
        for (int i = 0; i < 2; i++) {
            System.out.println("Jogador " + (i + 1) + ", digite seu nome:");
            jogadores[i] = new Jogador(entrada.next());
        }
    }

    public void distribuirCartas(int qtdCartas) {
        System.out.println("-----------DISTRIBUINDO BARALHO-----------\n");
        for (Jogador jogadore : jogadores) {
            jogadore.setCartas(BARALHO.distribuirCartas(qtdCartas));
        }
        cartasDescartadas = new Carta[BARALHO.getCARTAS().length - BARALHO.getContador()];

    }

    public void mostrarCartas() {
        for (Jogador jogadore : jogadores) {
            jogadore.mostrarCartas();
        }
    }

    public Carta obterCartaBaralho() {
        int indice = BARALHO.getUltimaCarta();
        Carta cartaRetirada = BARALHO.getCARTAS()[indice];
        BARALHO.setUltimaCarta(indice - 1);
        return cartaRetirada;
    } //Retira sempre a ultima carta do baralho

    public Carta obterCartaRestoBaralho() {
        Carta carta = cartasDescartadas[indiceDescarte - 1];
        indiceDescarte--;
        cartasDescartadas[indiceDescarte] = null;
        return carta;
    } //Retira sempre a ultima carta do resto do baralho

    public void descartarCartaMao(int index, Jogador jogador, Carta c) {
        Carta cartaDescarte = jogador.getCartas()[index];
        jogador.getCartas()[index] = c;
        descartarCartaObtida(cartaDescarte);
    } //Descarta uma carta esécífica que esta na "mao" do jogador

    public void descartarCartaObtida(Carta carta) {
        cartasDescartadas[indiceDescarte] = carta;
        System.err.println("{ " + carta.toString().toUpperCase() + " }" + " Descartada!");
        indiceDescarte++;
    } //Descarta a carta obtida, seja ela do baralho ou do resto do baralho

    public void descarte(Carta carta, Jogador jogador) {
        // Metodo que descarta uma carta de um jogador especifico
        // Por isso e necessario passar como parametro a carta que deseja descarta
        // e o jogador
        int indice;
        mostrarCartasJogador(jogador);
        System.out.println("[9] " + carta.toString().toUpperCase());
        System.out.println("\n--------------------------------------------\n");
        System.out.println("[x] Ecolha o Indece da Carta para Descarte");
        indice = entrada.nextInt();
        // Se indice for 9, o metodo descarta a carta que acabou de ser adicionada
        // Seja ela do baralho ou do resto do baralho e não adicona a nao adiciona
        // A mesma na "mao" do jogador
        // Caso contrario, ele descarta a carta selecionada de acordo com o indice
        if (indice == 9) {
            descartarCartaObtida(carta);
        } else {
            descartarCartaMao(indice, jogador, carta);
        }

    }

    public void mostrarCartasJogador(Jogador jogador) {
        int i = 0;
        for (Carta c : jogador.getCartas()) {
            System.out.println("[" + i + "] " + c.toString());
            i++;
        }
    }

    //Metodo responsavel por obter as cartas de acordo com opçao selecionada
    private Carta obterCarta() {
        int op;
        Carta carta;
        if (cartasDescartadas[0] != null) {
            System.out.println("1 - Obter carta do baralho");
            System.out.println("2 - Obter carta do baralho de descartes");
            op = entrada.nextInt();
            System.out.println("--------------------------------------------");
            // Metodo(Menu*) responsavel por obter as cartas de acordo com opçao selecionada
            switch (op) {
                case 1:
                    carta = obterCartaBaralho();
                    contadorVitoria++;
                    return carta;
                case 2:
                    carta = obterCartaRestoBaralho();
                    return carta;
                default:
                    break;
            }
        }
        System.out.println("1 - Obter carta do baralho");
        op = entrada.nextInt();
        System.out.println("--------------------------------------------\n");

        carta = obterCartaBaralho();
        return carta;

    }

    public void vitTrinca(Jogador jogador) {
        for (int c = 0; c < 9; c++) {
            Carta a = jogador.getCartas()[c];
            for (int d = 1; d < 9; d++) {
                Carta b = jogador.getCartas()[d];
                if (a.getFace().equals(b.getFace())) {
                    for (int h = d + 1; h < 9; h++) {
                        Carta e = jogador.getCartas()[h];
                        if (b.getFace().equals(e.getFace())) {
                            vitoria++;
                        }
                    }
                }
            }
        }
    } //Verifica condição de vitoria da Trinca percorrendo as posições comparando!

    public void vitSequencia(Jogador jogador) {
        for (int c = 0; c < 9; c++) {
            Carta a = jogador.getCartas()[c];
            for (int d = 0; d < 9; d++) {
                Carta b = jogador.getCartas()[d];
                if (b.getFace() == (a.getFace() + 1) && a.getNaipe().equals(b.getNaipe())) {
                    for (int h = 0; h < 9; h++) {
                        Carta e = jogador.getCartas()[h];
                        if (e.getFace() == (b.getFace() + 1) && b.getNaipe().equals(e.getNaipe())) {
                            vitoria++;
                        }
                    }
                }
            }
        }

    } //Verifica condição de vitoria da Sequencia percorrendo as posições comparando!

    public void jogar() {
        int confereVitoria = 0; // tirei a inicialização para a IDE parar de reclamar
        do { //Menu visual para usuário solicitar a check de vitória
            for (Jogador jogador : jogadores) {
                System.out.println("\n---------------------------");
                System.out.println("( " + jogador.getNOME().toUpperCase() + " )" + " Sua Vez de Jogar!");
                System.out.println("---------------------------\n");
                Carta c = obterCarta();
                descarte(c, jogador);
                System.out.println("\n");
                System.out.println("\t\t" + jogador.getNOME().toUpperCase() + " - Você Ganhou?");
                System.out.println("1 - SIM");
                System.out.println("2 - NÃO");
                confereVitoria = entrada.nextInt();
                
                if (confereVitoria == 1) {
                    vitTrinca(jogador);
                    if (vitoria < 3) {
                        vitSequencia(jogador);
                    }
                    if (vitoria < 3) {
                        System.err.println("\t\tVOCÊ NÃO GANHOU!");
                        vitoria = 0;
                    }
                }
                if (vitoria >= 3) {
                    System.out.println("\t\t" + jogador.getNOME().toUpperCase() + " VOCÊ VENCEU");
                    break;
                }
            }
        } while (contadorVitoria <= 32);
        if (contadorVitoria >= 32 && vitoria <= 3) {
            System.out.println("AS CARTAS DO BARALHO ACABARAM, NEMHUM JOGADOR VENCEU!");
        }
    }

    public Jogador[] getJogadores() {
        return jogadores;
    }

    public static void main(String[] args) {
        JogoDeCartasPifPaf executar = new JogoDeCartasPifPaf();
        executar.iniciarJogo();
        executar.distribuirCartas(9);
        executar.mostrarCartas();
        executar.jogar();

    } // Chamador de Funções!!!
}
