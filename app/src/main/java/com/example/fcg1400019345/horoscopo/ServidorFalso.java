package com.example.fcg1400019345.horoscopo;

/**
 * Created by xubuntu-developer on 9/3/15.
 */

public class ServidorFalso {
    public String[][] pegaDados() {
        String[][] dados1 = {
                {"1442361600","Áries", "Tudo de bom"},
                {"1442361600","Touro","Você é muito Sortudo(a)"},
                {"1442361600","Gêmeos","      - Maravilha"},
                {"1442361600","Câncer ","     - Tudo de bom"},
                {"1442361600","Leão      ","  - Você vai ser muito feliz"},
                {"1442361600","Virgem   ","   - Tudo de bom"},
                {"1442361600","Libra   ","    - Tudo de bom"},
                {"1442361600","Escorpião ","  - Você é muito Esperto"},
                {"1442361600","Sargitário "," - Tudo de bom, Seja Feliz"},
                {"1442361600","Capricórnio ","- Tudo de bom"},
                {"1442361600","Aquário ","    - Tudo de bom"},
                {"1442361600","Peixes  ","    - Tudo de bom"}

        };

        String[][] dados2 = {
                {"1442448000","Áries", "Tudo de bom"},
                {"1442448000","Touro","Você é muito Sortudo(a)"},
                {"1442448000","Gêmeos","      - Maravilha"},
                {"1442448000","Câncer ","     - Tudo de bom"},
                {"1442448000","Leão      ","  - Você vai ser muito feliz"},
                {"1442448000","Virgem   ","   - Tudo de bom"},
                {"1442448000","Libra   ","    - Tudo de bom"},
                {"1442448000","Escorpião ","  - Você é muito Esperto"},
                {"1442448000","Sargitário "," - Tudo de bom, Seja Feliz"},
                {"1442448000","Capricórnio ","- Tudo de bom"},
                {"1442448000","Aquário ","    - Tudo de bom"},
                {"1442448000","Peixes  ","    - Tudo de bom"}
//
//                {"1442448000", "Áres        - Tudo Maravilhoso"},
//                {"1442448000","Touro       - Jogue na Mega Sena  "},
//                {"1442448000", "Gêmeos      - Tudo Maravilhoso"},
//                {"1442448000","Câncer      - Tudo Maravilhoso"},
//                {"1442448000", "Leão        - Tudo Maravilhoso"},
//                {"1442448000", "Virgem      - Tudo Maravilhoso"},
//                {"1442448000","Libra       - Tudo Maravilhoso"},
//                {"1442448000","Escorpião   - Tudo Maravilhoso"},
//                {"1442448000","Sargitário  - Tudo Maravilhoso"},
//                {"1442448000","Capricórnio - Tudo Maravilhoso"},
//                {"1442448000","Aquário     - Tudo Maravilhoso"},
//                {"1442448000","Peixes      - Tudo Maravilhoso"}
        };

        // Para não pegar sempre o mesmo...
        long time = System.currentTimeMillis() / 1000;
        if (time % 2 == 0) {
            return dados1;
        } else {
            return dados2;
        }
    }
}



