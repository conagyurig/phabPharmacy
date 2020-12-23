package Website;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import static java.lang.String.valueOf;

@WebServlet(urlPatterns = "/browse",loadOnStartup = 0)
public class ServletBrowse extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //ProductDB pdb = new ProductDB();
        //BasketDB bdb = new BasketDB();
        resp.setContentType("text/html");
        PrintWriter writer = resp.getWriter();
        String HTML= htmlOutput();
        writer.println(HTML);

                ArrayList<String> headers = new ArrayList<>();
                ArrayList<String> headerURLs = new ArrayList<>();
                headers.add("Cold and Flu");
                headers.add("Skincare");
                headers.add("Headaches and Pain Relief");
                headers.add("Digestion");
                headers.add("Allergy");
                headers.add("First Aid");
                headerURLs.add("cold_and_flu");
                headerURLs.add("skincare");
                headerURLs.add("headaches_and_pain_relief");
                headerURLs.add("digestion");
                headerURLs.add("allergy");
                headerURLs.add("first_aid");
                int j=1;
                Product p = LoginDAO.getProduct(j);
                for (int i=0;i<6;i++) {
                    writer.println("<section>\n" +
                            "<h2 id=\""+headerURLs.get(i)+"\">" + headers.get(i) + "</h2>\n");
                    while (p.category.equals(headers.get(i))) {
                        DecimalFormat df = new DecimalFormat("0.00");
                        String price = valueOf(df.format(p.price));
                        int max = p.limited ? 1 : 5;
                        writer.print("<div class=\"relative\">\n");
                        if (p.limited){
                            writer.print("<label class=\"tooltip\"><center>" + p.name + "<br>" + p.description + "</center>\n" +
                                    "<span class=\"tooltiptext\"><i>Limited to one per customer</i></span></label><br>\n");
                        }
                        else{
                            writer.print("<label><center>" + p.name + "<br>" + p.description + "</center></label><br>\n");
                        }
                        writer.print("<label><center>£" + price + "</label></center><br>\n" +
                                "<div class=\"absolute\">\n" +
                                "<form action=\"browse\" method=\"post\">\n" +
                                "<input name=\"number" + j + "\" type=\"number\" size=\"5\" min=\"0\" max=\"" + max + "\">\n" +
                                "<input name=\"position\" type=\"hidden\"value=\"" + j + "\">\n" +
                                "<input name=\"button" + j + "\" type=\"submit\"class=\"buttonStyle\" value=\"Add to Basket\">\n" +
                                //"<button name=\"button\" type=\"submit\" class=\"buttonStyle\" value=\"" + j + "\">Add to Basket</button>\n" +
                                "</form>\n" +
                                "</div>\n" +
                                "</div>");
                        j++;
                        p = LoginDAO.getProduct(j);
                    }
                    writer.println("</section>");
                }

                writer.println("<script>\n" +
                        "    function addBasket(){\n" +
                        "        var num=document.getElementById(\"number1\").value;\n" +
                        "        document.getElementById(\"basket\").innerHTML = num\n" +
                        "        document.getElementById(\"basket\").style.fontFamily =\"Arial, Helvetica, sans-serif\";\n" +
                        "    }\n" +
                        "</script>\n" +
                        "</body>\n" +
                        "</html>");

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter writer = resp.getWriter();
        int tableSize = LoginDAO.tableSize("products");
        writer.println("<p> size:" + tableSize + "</p>");
        writer.println("<p>button pressed</p>");
        String val= req.getParameter("position");
        int value = Integer.parseInt(val);
        int q = Integer.parseInt(req.getParameter("number"));
        writer.print("<p> button num" + value + "</p>");
        Product p = LoginDAO.getProduct(value);
        LoginDAO.addToBasket(p, value);
        writer.print("<p>Product " + p.name + "</p>");
        writer.print("<p>Quantity: " + q + "</p>");



    public String htmlOutput(){
        String output="<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "    <meta charset=\"utf-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width\">\n" +
                "    <title>Browse</title>\n" +
                "    <!-- Import Icon Library -->\n" +
                "    <link rel=\"stylesheet\" href=\"https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css\">\n" +
                "    <!-- Creates navigation bar -->\n" +
                "    <style>\n" +
                "        body {font-family: Arial, Helvetica, sans-serif;}\n" +
                "        .navbar {\n" +
                "            width: 100%;\n" +
                "            background-color: #555;\n" +
                "            overflow: auto;\n" +
                "        }\n" +
                "        .navbar a {\n" +
                "            float: left;\n" +
                "            padding: 12px;\n" +
                "            color: white;\n" +
                "            text-decoration: none;\n" +
                "            font-size: 17px;\n" +
                "        }\n" +
                "        @media screen and (max-width: 500px) {\n" +
                "            .navbar a {\n" +
                "                float: none;\n" +
                "                display: block;\n" +
                "            }\n" +
                "        }\n" +
                "        .dropdown {\n" +
                "            float: left;\n" +
                "            overflow: hidden;\n" +
                "        }\n" +
                "        .dropdown .dropbtn {\n" +
                "            font-size: 16px;\n" +
                "            border: none;\n" +
                "            outline: none;\n" +
                "            color: white;\n" +
                "            padding: 14px 16px;\n" +
                "            background-color: inherit;\n" +
                "            font-family: inherit;\n" +
                "            margin: 0;\n" +
                "        }\n" +
                "        .navbar a:hover, .dropdown:hover .dropbtn {\n" +
                "            background-color: #000;\n" +
                "        }\n" +
                "        .dropdown-content {\n" +
                "            display: none;\n" +
                "            position: absolute;\n" +
                "            background-color: #f9f9f9;\n" +
                "            min-width: 160px;\n" +
                "            box-shadow: 0px 8px 16px 0px rgba(0,0,0,0.2);\n" +
                "            z-index: 1;\n" +
                "        }\n" +
                "        .dropdown-content a {\n" +
                "            float: none;\n" +
                "            color: black;\n" +
                "            padding: 12px 16px;\n" +
                "            text-decoration: none;\n" +
                "            display: block;\n" +
                "            text-align: left;\n" +
                "        }\n" +
                "        .dropdown-content a:hover {\n" +
                "            background-color: #ddd;\n" +
                "        }\n" +
                "        .dropdown:hover .dropdown-content {\n" +
                "            display: block;\n" +
                "        }\n" +
                "        div.absolute {\n" +
                "            position: absolute;\n" +
                "            top: 140px;\n" +
                "            width: 200px;\n" +
                "            height: 30px;\n" +
                "        }\n" +
                "        div.relative{\n" +
                "            position: relative;\n" +
                "            width: 200px;\n" +
                "            height: 150px;\n" +
                "            display: inline;\n" +
                "            border: 1px solid black;\n" +
                "            padding: 15px;\n" +
                "            margin: 5px;\n" +
                "            float: left;\n" +
                "        }\n" +
                "        .buttonStyle{\n" +
                "            background-color: #00B8C5;\n" +
                "            border: none;\n" +
                "            color: white;\n" +
                "            padding: 5px 25px;\n" +
                "            text-align: center;\n" +
                "            text-decoration: none;\n" +
                "            display: inline-block;\n" +
                "            font-size: 16px;\n" +
                "            margin: 4px 2px;\n" +
                "            cursor: pointer;\n" +
                "        }\n" +
                "        section{\n" +
                "            display: table-row;\n" +
                "        }\n" +
                "        .tooltip{\n" +
                "            position: relative;\n" +
                "            display: inline;\n" +
                "        }\n" +
                "        .tooltip .tooltiptext {\n" +
                "            visibility: hidden;\n" +
                "            width: 120px;\n" +
                "            background-color: black;\n" +
                "            color: #fff;\n" +
                "            text-align: center;\n" +
                "            border-radius: 6px;\n" +
                "            padding: 5px 0;\n" +
                "            position: absolute;\n" +
                "            z-index: 1;\n" +
                "        }\n" +
                "        .tooltip:hover .tooltiptext {\n" +
                "            visibility: visible;\n" +
                "        }\n" +
                "    </style>\n" +
                "\n" +
                "</head>\n" +
                "<body>\n" +
                "<div class=\"navbar\">\n" +
                "    <a href=\"https://phabpharmacy.herokuapp.com/home\"><i class=\"fa fa-fw fa-home\"></i>Home</a>\n" +
                "    <div class=\"dropdown\">\n" +
                "        <button style=\"background-color: #00B8C5;\" class= \"dropbtn\"><i class=\"fa fa-fw fa-search\"></i>Browse<i class=\"fa fa-caret-down\"></i></button>\n" +
                "        <div class=\"dropdown-content\">\n" +
                "           <a href=\"https://phabpharmacy.herokuapp.com/browse#cold_and_flu\">Cold and Flu</a>\n" +
                "           <a href=\"https://phabpharmacy.herokuapp.com/browse#skincare\">Skincare</a>\n" +
                "           <a href=\"https://phabpharmacy.herokuapp.com/browse#headaches_and_pain_relief\">Headaches and Pain Relief</a>\n" +
                "           <a href=\"https://phabpharmacy.herokuapp.com/browse#digestion\">Digestion</a>\n" +
                "           <a href=\"https://phabpharmacy.herokuapp.com/browse#allergy\">Allergy</a>\n" +
                "           <a href=\"https://phabpharmacy.herokuapp.com/browse#first_aid\">First Aid</a>\n" +
                "        </div>\n" +
                "    </div>\n" +
                "    <a href=\"https://phabpharmacy.herokuapp.com/login\"><i class=\"fa fa-fw fa-user\"></i>Login</a>\n" +
                "    <a href=\"https://phabpharmacy.herokuapp.com/register\"><i class=\"fa fa-fw fa-user-plus\"></i>Register</a>\n" +
                "    <a href=\"https://phabpharmacy.herokuapp.com/basket\" class=\"fa fa-fw fa-shopping-basket\"><b id=\"basket\"></b></a>\n" +
                "</div>";
        return output;
    }
}
