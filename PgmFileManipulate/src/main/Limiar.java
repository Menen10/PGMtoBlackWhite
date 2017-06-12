package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;


public class Limiar {

	
	public static void limiarizar(File pgm, int limiar) throws FileNotFoundException, IOException{
		
		
		/** Cria o leitor*/
		try (BufferedReader br = new BufferedReader(new FileReader(pgm))) {
			
			int indiceDoPonto = pgm.toString().indexOf(".");
		    String nomeArquivo = pgm.toString().substring(0,indiceDoPonto);
			
			/** Nome para cada arquivo*/
			File arquivoSaida = new File(nomeArquivo+ "Limiar"+limiar+".pgm"); 
			
			/** Cria o escritor*/
			try(BufferedWriter bw = new BufferedWriter(new FileWriter(arquivoSaida))){    

				
			 	/** Passa como parametro ao scanner o buffered reader*/
				Scanner en = new Scanner(br);
	           
	            int lin=0,col=0,max=0;
	            
	            
	            
	            /** Pula o protocolo P2 com o nextline fora do "do while"
	             *  Tentará passar para os inteiros os valores consecutivos de inteiros do .pgm file
	             *  Provavelmente ocorrera um input mismatch devido ao protocolo */
	            
	            do{
	            try {
					en.nextLine();
					/** Captura o width e height da imagem*/
	            	lin = en.nextInt();
	 	            col = en.nextInt();
	 	            max = en.nextInt();	
	            	
				} catch (InputMismatchException e) {
				
					lin=0;
					col=0;
					max=0;
					
				}
	            }while(lin == 0 || col == 0);
	            
	            
	            bw.write("P2");
            	bw.newLine();
            	bw.write("#Created by a misery existence");
            	bw.newLine();
            	bw.write(lin+" "+ col);
            	bw.newLine();
            	if(max==255){
            		bw.write(max+"");	
            	}else{
            		bw.write("255");
            	}
            	
            	bw.newLine();
                
	            /** Cria a matriz com as dimensoes de width e height*/
	            int pixels[][] = new int[lin][col];
	            
	            for (int x = 0; x < lin; x++) {
					
            		for (int y = 0; y < col; y++) {
            			
            			/** Popula a matriz com os pixels da imagem pgm*/
						pixels[x][y] = en.nextInt();
						
						/** Verifica se este o pixels é maior ou igual ao limiar em questão*/
						if(pixels[x][y] >= limiar){
						/** caso seja, atribui valor de cor branco*/
            				bw.write("255 ");
            			/** caso não, atribui valor de cor preto*/
            			}else{
            				bw.write("0 ");
            			}
            		}
            		bw.newLine();
            	}      
	           
					
	           /** Fecha o scannner*/
	           en.close();
	         
				}/** Fecha o Escritor*/
			
			
	        }/** Fecha o leitor*/
    
	}/** Fecha o metodo*/
	
}/** Fecha a classe*/

