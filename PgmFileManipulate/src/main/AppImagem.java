package main;

import java.io.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.*;

/**
 * 
 *   imagens/Open16.gif
 *   imagens/Save16.gif
 */

@SuppressWarnings("serial")
public class AppImagem extends JPanel implements ActionListener  {
    private static final String newline = "\n";
    JButton openButton, limiarButton;
    JTextArea areaDeTexto;
    JFileChooser arquivo;
    JSlider slider;
    int limiar;
    JLabel jl;
    
    
    
    public AppImagem() {
       super(new BorderLayout());

        /** Cria a area de texto primeiramente, devido aos action listeners */
       
        
        areaDeTexto = new JTextArea(15,30);
        areaDeTexto.setMargin(new Insets(10,10,10,10));
        
        areaDeTexto.setEditable(false);
        /** Inicializa o scrollpane com a TextArea como paramentro */
        JScrollPane scrollPane = new JScrollPane(areaDeTexto);
        
        //scrollPane.setBounds(10, 100, 300, 300);
        jl = new JLabel("Limiar: " + 128);
        jl.setBounds(10, 10, 20, 10);
        //jl.setLocation(f);
        jl.setVisible(true);
        
        
        arquivo = new JFileChooser();
        arquivo.setAcceptAllFileFilterUsed(false);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("PGM File", "pgm");
        arquivo.addChoosableFileFilter(filter);
     
        /** Cria o botao de abrir, usando um icone*/
        openButton = new JButton("Abrir Arquivo PGM", createImageIcon("imagens/Open16.gif"));
       
        openButton.addActionListener(this);

        /** Cria o botao de abrir, usando um icone*/
        limiarButton = new JButton("Aplicar Limiar", createImageIcon("imagens/Save16.gif"));
        limiarButton.addActionListener(this);
        
        
        slider = new JSlider(1, 254);
        slider.setOrientation(SwingConstants.HORIZONTAL);
        slider.setValue(128);
        limiar = slider.getValue();
        slider.setBounds(20, 30, 200, 20);
        slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                limiar = slider.getValue();
                jl.setText("Limiar : "+ limiar);
                //areaDeTexto.append("Limiar: " + limiar + newline);
                
            
            }
        });
        

        /** Por quest�o de estetica e layout, coloca os botoes em um painel separado*/
        JPanel buttonPanel = new JPanel(); 
       // JPanel sliderPanel = new JPanel();
        
        buttonPanel.add(openButton);
        buttonPanel.add(limiarButton);
        buttonPanel.add(jl);
        buttonPanel.add(slider);
        
        
        
        
        /** adiciona os botoes e scrollpane ao painel principal.*/
        
        add(buttonPanel,BorderLayout.PAGE_START);
        
        add(scrollPane, BorderLayout.CENTER);
        
        
    }
    
   

    @Override
    public void actionPerformed(ActionEvent e) {

        
    	/** Caso tenha sido clicado o botão  de abrir*/
        if(e.getSource() == openButton) {
            int retorno = arquivo.showOpenDialog(AppImagem.this);
            
            if (retorno == JFileChooser.APPROVE_OPTION) {
            	
         /** Pega o arquivo selecionado*/
                File file = arquivo.getSelectedFile();
              
         /** Coloca na tela o nome do arquivo que está aberto.*/
                areaDeTexto.append("Aberto: " + file.getName() + "." + newline);
            }
            
            areaDeTexto.setCaretPosition(areaDeTexto.getDocument().getLength());

         /** Caso tenha sido selecionado o botao de gerar os limiares. */
        } else if (e.getSource() == limiarButton) {
            
            
        	File file = arquivo.getSelectedFile();
        	try {
                    
               
        		Limiar.limiarizar(file,limiar);
        
        		areaDeTexto.append("Limiarização de " + file.getName() + " efetuado com sucesso." + newline);
				
        	} catch (IOException e1) {
					JOptionPane.showMessageDialog(null,"Erro de entrada de dados");
            }catch(NullPointerException nullex){

                System.out.println("Limiar: " + limiar);
            	JOptionPane.showMessageDialog(null, "Nenhum arquivo selecionado!");
            }
                
            areaDeTexto.setCaretPosition(areaDeTexto.getDocument().getLength());
        }
    }

    /** Retorna os icones da imagem, ou null se a chave for invalida. */
    protected static ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = AppImagem.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
        	JOptionPane.showMessageDialog(null, "Não � possivel achar o arquivo: " + path);
            return null;
        }
    }

  
    private static void inicializadorDeTela() {
    	/** Cria e configura a janela.*/
        JFrame frame = new JFrame("Limiarizador de Imagens");
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      
        /** Adiciona o conteudo para janela*/
        frame.add(new AppImagem());

        /** Inicia a janela*/
        frame.pack();
        frame.setLocationRelativeTo(null);
       frame.setSize(600,400);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        /**Criando e mostrando a interface.*/
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                /**Desativa negrito*/
                UIManager.put("swing.boldMetal", Boolean.FALSE); 
                inicializadorDeTela();
            }
        });
    }
    
}
