import csv.*;



class TryCSV{

    public static void main(String[] args){

        String[] header = {"one", "two", "three", "four", "five"};

        CsvOp newCsvOp = new CsvOp("/home/pradeep/temp/newcsv.csv");

        boolean result = newCsvOp.createCsvFile(header);
        System.out.println(result);                           

    }

}