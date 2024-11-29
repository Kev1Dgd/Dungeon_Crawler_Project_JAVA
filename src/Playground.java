import javax.imageio.ImageIO;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;

public class Playground {
    private ArrayList<Sprite> environment = new ArrayList<>();

    public Playground(String pathName) {
        try {
            final Image imageTree = ImageIO.read(new File("C:/Users/kevin/Documents/TD4/img/Arbres1.png"));
            final Image imageGrass = ImageIO.read(new File("C:/Users/kevin/Documents/TD4/img/grass.png"));
            final Image imageRock = ImageIO.read(new File("C:/Users/kevin/Documents/TD4/img/Rock.png"));
            final Image imageTrap = ImageIO.read(new File("C:/Users/kevin/Documents/TD4/img/trap.png"));
            final Image imageCenter = ImageIO.read(new File("C:/Users/kevin/Documents/TD4/img/Center.png"));
            final Image imageShop = ImageIO.read(new File("C:/Users/kevin/Documents/TD4/img/shop.png"));
            final Image imagePath = ImageIO.read(new File("C:/Users/kevin/Documents/TD4/img/path.png"));
            final Image imageHouse1 = ImageIO.read(new File("C:/Users/kevin/Documents/TD4/img/House1.png"));
            final Image imageLake = ImageIO.read(new File("C:/Users/kevin/Documents/TD4/img/LakeF.png"));

            final int imageTreeWidth = imageTree.getWidth(null);
            final int imageTreeHeight = imageTree.getHeight(null);
            final int imageGrassWidth = imageGrass.getWidth(null);
            final int imageGrassHeight = imageGrass.getHeight(null);
            final int imageRockWidth = imageRock.getWidth(null);
            final int imageRockHeight = imageRock.getHeight(null);
            final int imageTrapWidth = imageTrap.getWidth(null);
            final int imageTrapHeight = imageTrap.getHeight(null);
            final int imageCenterWidth = imageCenter.getWidth(null);
            final int imageCenterHeight = imageCenter.getHeight(null);
            final int imageShopWidth = imageShop.getWidth(null);
            final int imageShopHeight = imageShop.getHeight(null);
            final int imagePathWidth = imagePath.getWidth(null);
            final int imagePathHeight = imagePath.getHeight(null);
            final int imageHouse1Width = imageHouse1.getWidth(null);
            final int imageHouse1Height = imageHouse1.getHeight(null);

            final int imageLakeWidth = imageLake.getWidth(null);
            final int imageLakeHeight = imageLake.getHeight(null);

            BufferedReader bufferedReader = new BufferedReader(new FileReader(pathName));
            String line = bufferedReader.readLine();
            int lineNumber = 0;

            while (line != null) {
                int columnNumber = 0;
                for (byte element : line.getBytes(StandardCharsets.UTF_8)) {
                    switch (element) {
                        case 'T':
                            environment.add(new Sprite(
                                    imageGrass,
                                    columnNumber * imageGrassWidth,
                                    lineNumber * imageGrassHeight,
                                    imageGrassWidth,
                                    imageGrassHeight
                            ));
                            environment.add(new Sprite(
                                    imageGrass,
                                    (columnNumber+1) * imageGrassWidth,
                                    (lineNumber+1) * imageGrassHeight,
                                    imageGrassWidth,
                                    imageGrassHeight
                            ));
                            environment.add(new Sprite(
                                    imageGrass,
                                    columnNumber * imageGrassWidth,
                                    (lineNumber+1) * imageGrassHeight,
                                    imageGrassWidth,
                                    imageGrassHeight
                            ));
                            environment.add(new SolidSprite(
                                    imageTree,
                                    columnNumber * imageTreeWidth,
                                    lineNumber * imageTreeHeight,
                                    imageTreeWidth,
                                    imageTreeHeight
                            ));
                            break;

                        case 'G':
                            environment.add(new Sprite(
                                    imageGrass,
                                    columnNumber * imageGrassWidth,
                                    lineNumber * imageGrassHeight,
                                    imageGrassWidth,
                                    imageGrassHeight
                            ));
                            break;

                        case 'P':
                            environment.add(new Sprite(
                                    imagePath,
                                    columnNumber * imagePathWidth,
                                    lineNumber * imagePathHeight,
                                    imagePathWidth,
                                    imagePathHeight
                            ));
                            break;

                        case 'R':
                            environment.add(new Sprite(
                                    imageGrass,
                                    columnNumber * imageGrassWidth+0.9,
                                    lineNumber * imageGrassHeight+0.9,
                                    imageGrassWidth,
                                    imageGrassHeight
                            ));
                            environment.add(new SolidSprite(
                                    imageRock,
                                    columnNumber * imageRockWidth,
                                    lineNumber * imageRockHeight,
                                    imageRockWidth,
                                    imageRockHeight
                            ));

                            break;

                        case 'X':
                            environment.add(new SolidSprite(
                                    imageTrap,
                                    columnNumber * imageRockWidth,
                                    lineNumber * imageRockHeight,
                                    imageTrapWidth,
                                    imageTrapHeight
                            ));
                            break;

                        case 'C':
                            environment.add(new Sprite(
                                    imageGrass,
                                    columnNumber * imageGrassWidth,
                                    lineNumber * imageGrassHeight,
                                    imageGrassWidth,
                                    imageGrassHeight
                            ));
                            environment.add(new Sprite(
                                    imageGrass,
                                    (columnNumber+1) * imageGrassWidth,
                                    lineNumber * imageGrassHeight,
                                    imageGrassWidth,
                                    imageGrassHeight
                            ));
                            environment.add(new Sprite(
                                    imagePath,
                                    columnNumber * imagePathWidth,
                                    (lineNumber+1) * imagePathHeight,
                                    imagePathWidth,
                                    imagePathHeight
                            ));
                            environment.add(new Sprite(
                                    imagePath,
                                    (columnNumber+1) * imagePathWidth,
                                    (lineNumber+1) * imagePathHeight,
                                    imagePathWidth,
                                    imagePathHeight
                            ));
                            environment.add(new SolidSprite(
                                    imageCenter,
                                    columnNumber * imageGrassWidth+8,
                                    lineNumber * imageGrassHeight,
                                    imageCenterWidth,
                                    imageCenterHeight
                            ));
                            break;

                        case 'S':
                            environment.add(new Sprite(
                                    imageGrass,
                                    columnNumber * imageGrassWidth,
                                    lineNumber * imageGrassHeight,
                                    imageGrassWidth,
                                    imageGrassHeight
                            ));
                            environment.add(new Sprite(
                                    imageGrass,
                                    (columnNumber+1) * imageGrassWidth,
                                    lineNumber * imageGrassHeight,
                                    imageGrassWidth,
                                    imageGrassHeight
                            ));
                            environment.add(new Sprite(
                                    imagePath,
                                    columnNumber * imagePathWidth,
                                    (lineNumber+1) * imagePathHeight,
                                    imagePathWidth,
                                    imagePathHeight
                            ));
                            environment.add(new Sprite(
                                    imagePath,
                                    (columnNumber+1) * imagePathWidth,
                                    (lineNumber+1) * imagePathHeight,
                                    imagePathWidth,
                                    imagePathHeight
                            ));
                            environment.add(new SolidSprite(
                                    imageShop,
                                    columnNumber * imageGrassWidth+8,
                                    lineNumber * imageGrassHeight,
                                    imageCenterWidth,
                                    imageCenterHeight
                            ));
                            break;

                        case 'H':
                            environment.add(new Sprite(
                                    imageGrass,
                                    columnNumber * imageGrassWidth,
                                    lineNumber * imageGrassHeight,
                                    imageGrassWidth,
                                    imageGrassHeight
                            ));
                            environment.add(new Sprite(
                                    imageGrass,
                                    (columnNumber+1) * imageGrassWidth,
                                    lineNumber * imageGrassHeight,
                                    imageGrassWidth,
                                    imageGrassHeight
                            ));
                            environment.add(new Sprite(
                                    imagePath,
                                    columnNumber * imagePathWidth,
                                    (lineNumber+1) * imagePathHeight,
                                    imagePathWidth,
                                    imagePathHeight
                            ));
                            environment.add(new Sprite(
                                    imagePath,
                                    (columnNumber+1) * imagePathWidth,
                                    (lineNumber+1) * imagePathHeight,
                                    imagePathWidth,
                                    imagePathHeight
                            ));
                            environment.add(new SolidSprite(
                                    imageHouse1,
                                    columnNumber * imageHouse1Width-100,
                                    lineNumber * imageHouse1Height-100,
                                    imageHouse1Width,
                                    imageHouse1Height
                            ));
                            break;

                        case 'L':
                            environment.add(new Sprite(
                                    imageGrass,
                                    columnNumber * imageGrassWidth,
                                    lineNumber * imageGrassHeight,
                                    imageGrassWidth,
                                    imageGrassHeight
                            ));
                            environment.add(new Sprite(
                                    imageGrass,
                                    columnNumber * imageGrassWidth+64,
                                    lineNumber * imageGrassHeight,
                                    imageGrassWidth,
                                    imageGrassHeight
                            ));
                            environment.add(new Sprite(
                                    imageGrass,
                                    columnNumber * imageGrassWidth+128,
                                    lineNumber * imageGrassHeight,
                                    imageGrassWidth,
                                    imageGrassHeight
                            ));
                            environment.add(new Sprite(
                                    imageGrass,
                                    columnNumber * imageGrassWidth,
                                    lineNumber * imageGrassHeight+64,
                                    imageGrassWidth,
                                    imageGrassHeight
                            ));
                            environment.add(new Sprite(
                                    imageGrass,
                                    columnNumber * imageGrassWidth,
                                    lineNumber * imageGrassHeight+128,
                                    imageGrassWidth,
                                    imageGrassHeight
                            ));
                            environment.add(new Sprite(
                                    imageGrass,
                                    columnNumber * imageGrassWidth+64,
                                    lineNumber * imageGrassHeight+64,
                                    imageGrassWidth,
                                    imageGrassHeight
                            ));
                            environment.add(new Sprite(
                                    imageGrass,
                                    columnNumber * imageGrassWidth+64,
                                    lineNumber * imageGrassHeight+128,
                                    imageGrassWidth,
                                    imageGrassHeight
                            ));
                            environment.add(new Sprite(
                                    imageGrass,
                                    columnNumber * imageGrassWidth+128,
                                    lineNumber * imageGrassHeight+128,
                                    imageGrassWidth,
                                    imageGrassHeight
                            ));
                            environment.add(new Sprite(
                                    imageGrass,
                                    columnNumber * imageGrassWidth+128,
                                    lineNumber * imageGrassHeight+64,
                                    imageGrassWidth,
                                    imageGrassHeight
                            ));
                        environment.add(new SolidSprite(
                                imageLake,
                                960,
                                384,
                                imageLakeWidth,
                                imageLakeHeight
                        ));
                        break;

                    }
                    columnNumber++;
                }
                lineNumber++;
                line = bufferedReader.readLine();
            }
        } catch (IOException e) {
            System.err.println("Erreur lors de la création du Playground : " + e.getMessage());
            e.printStackTrace();
        }
    }

    public ArrayList<Sprite> getSolidSpriteList() {
        ArrayList<Sprite> solidSpriteArrayList = new ArrayList<>();
        for (Sprite sprite : environment) {
            if (sprite instanceof SolidSprite) solidSpriteArrayList.add(sprite);
        }
        return solidSpriteArrayList;
    }

    public ArrayList<Displayable> getSpriteList() {
        ArrayList<Displayable> displayableArrayList = new ArrayList<>();
        for (Sprite sprite : environment) {
            displayableArrayList.add(sprite);
        }
        return displayableArrayList;
    }
}
