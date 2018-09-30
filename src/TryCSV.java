import csv.*;



class TryCSV{

    public static void main(String[] args){

        String[] header = {"one", "two", "three", "four", "five"};

        CsvOp newCsvOp = new CsvOp("/home/pradeep/temp/newcsv.csv");

        //newCsvOp.createCsvFile();
        newCsvOp.addHeader(header);
        //newCsvOp.addRow("Pradeep, kavi, abhinav");
        //System.out.println(newCsvOp.getRow(2));
        //System.out.println(newCsvOp.getHeader());


        
    }

}