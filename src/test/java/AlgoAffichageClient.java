public class AlgoAffichageClient {
    public static void main(String[] args) {
        int nombreDeClient = 7;

        for(int i=0; i<nombreDeClient; i++) {
            if (i%3 == 0 && i != 0) {
                System.out.println("");
                System.out.print(i + " - ");
            } else {
                System.out.print(i + " - ");
            }
        }
    }
}
