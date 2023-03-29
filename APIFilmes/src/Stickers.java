import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class Stickers {
    
    void cria(InputStream pathImage, String nomeArquivo, String texto, InputStream imagemAvaliacao) throws Exception{
      
        //leitura da imagem

       // InputStream pathImage = new FileInputStream(new File("D:/Marley/Aplicações/ImersãoJava/APIFilmes/entrada/filme.jpg"));
       // InputStream pathImage = new URL("https://raw.githubusercontent.com/alura-cursos/imersao-java-2-api/main/MostPopularMovies_1.jpg").openStream();
        BufferedImage ImagemOriginal = ImageIO.read(pathImage);
        

        //cria nova imagem em memoria com transparencia e tamanho novo

        int Largura = ImagemOriginal.getWidth();
        int Altura = ImagemOriginal.getHeight();
        int novaAltura = Altura + 200;
        BufferedImage novaImagem = new BufferedImage(Largura, novaAltura, BufferedImage.TRANSLUCENT);
        
        //copiar imagem original para a nova em memoria

        Graphics2D graphics = (Graphics2D) novaImagem.getGraphics();
        graphics.drawImage(ImagemOriginal, 0, 0, null);

        //Imagem avaliação

        BufferedImage imagemSobreposicao = ImageIO.read(imagemAvaliacao);
        int posicaoImagemSobreposicaoX = 0;
        int posicaoImagemSobreposicaoY = novaAltura - imagemSobreposicao.getHeight();
        graphics.drawImage(imagemSobreposicao, posicaoImagemSobreposicaoX, posicaoImagemSobreposicaoY, null);


        //configurar a fonte

        Font fonte = new Font("Impact", Font.PLAIN, 84);
        graphics.setColor(Color.yellow);
        graphics.setFont(fonte);

        //Escrever uma frase na nova imagem

        FontMetrics fontMetrics = graphics.getFontMetrics();
        Rectangle2D areaEscrita = fontMetrics.getStringBounds(texto, graphics);
        int larguraTesxto = (int) areaEscrita.getWidth();
        int alturaTexto = (int) areaEscrita.getHeight();
        int posicaoTextoX = (Largura - larguraTesxto) / 2;
        int posicaoTextoY = (novaAltura - (alturaTexto / 2));// + (alturaTexto / 2) ;
        graphics.drawString(texto, posicaoTextoX, posicaoTextoY);

        FontRenderContext fontRenderContext = graphics.getFontRenderContext();
        var textLayout = new TextLayout(texto, fonte, fontRenderContext);
        Shape outline = textLayout.getOutline(null);
        AffineTransform transform = graphics.getTransform();
        transform.translate(posicaoTextoX, posicaoTextoY);
        graphics.setTransform(transform);

        BasicStroke outLineStroke = new BasicStroke(Largura * 0.004f);
        graphics.setStroke(outLineStroke);

        graphics.setColor(Color.BLACK);
        graphics.draw(outline);
        graphics.setClip(outline);

        //Escrever nova imagem em um arquivo
        ImageIO.write(novaImagem, "png", new File(nomeArquivo));

    }

}
