package Mars;

import java.util.Stack;

/**
 * Класс, который отвечает за создание генеалогических деревьев
 */
public class Tree {
    static String shift = "    ";

    /**
     * Выдает отчет о семье переданного марсианина в виде дерева
     * @param martian марсианин из семьи
     * @param <T> тип кодов
     * @return строковое представление семьи
     */
    public static<T> String getReport(Martian<T> martian){
        Martian<T> root = martian.findRoot();
        StringBuilder sb = new StringBuilder(root.toString() + "\n");

        writeChildren(root, sb, 1);

        return sb.toString();
    }

    /**
     * Записывает в строку с шифтом*глубину всех детей и рекурсивно их потомков
     * @param martian марсианин, от которого берем детей
     * @param sb строку для записи
     * @param deep количество шифтов
     * @param <T> тип кода
     */
    public static<T> void writeChildren(Martian<T> martian, StringBuilder sb, int deep){
        for(var child : martian.getChildren()){
            sb.append(shift.repeat(deep)).append(child.toString()).append("\n");
            writeChildren(child, sb, deep + 1);
        }
    }

    /**
     * Выдает семью марсиан, восстановленную из строковго представления
     * @param report стркоове представление семьи
     * @return самый старший марсианин в данной семье (корень)
     */
    public static<T> Martian<T> getTreeFromReport(String report) throws ClassNotFoundException {
        String[] splited = report.split("\n");
        String martianType = splited[0].split("\\(")[0];
        String codeType = splited[0].split("\\(")[1].split(":")[0];
        String currentCode = splited[0].split("\\(")[1].split(":")[1].split("\\)")[0];

        Innovator<T> root = createNewInnovator(codeType,currentCode);

        // будем добавлять  в стек новых марсиан при заходе в большую глубину и удалять при уходе в меньшую
        Stack<Innovator<T>> stc = new Stack<>();

        int deep = 0;
        Innovator<T> prev = root;
        for(int i = 1; i < splited.length; i++){
            // если зашли глубже
            if(countDeep(splited[i]) > deep){
                stc.push(prev);
                deep++;
            }
            // если полднимаемся с глубин
            while(countDeep(splited[i]) < deep){
                stc.pop();
                deep--;
            }

            currentCode = splited[i].split("\\(")[1].split(":")[1].split("\\)")[0];
            prev = createNewInnovator(codeType, currentCode);
            stc.peek().addChild(prev);
        }

        // если дерево консерваторов
        if (martianType.equals("ConservativeMartian"))
            return new Conservative(root);

        return root;
    }

    /**
     * Считает глубину вложенности для строки
     * @param str строка
     * @return глубина
     */
    public static int countDeep(String str){
        int res = 0;
        char[] ch = str.toCharArray();
        for(int i = 0; i < str.length(); i++){
            if (ch[i] == ' ')
                res++;
            else
                break;
        }
        return res / 4;
    }

    /**
     * Создает объект инноватора
     * @param codeType тип генетического кода
     * @param currentCode текущий генетический код
     * @return инноватор
     */
    public static<T> Innovator<T> createNewInnovator(String codeType, String currentCode){
        Innovator<T> res = new Innovator<>();

        switch (codeType){
            case "Integer":
                res = (Innovator<T>) new Innovator<Integer>(Integer.parseInt(currentCode));
                break;
            case "Double":
                res = (Innovator<T>) new Innovator<Double>(Double.parseDouble(currentCode));
                break;
            case "String":
                res = (Innovator<T>) new Innovator<String>(currentCode);
                break;
        }

        return res;
    }
}
