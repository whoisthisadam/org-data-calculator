package com.chumachenko.coursework.util;

import javafx.scene.control.Alert;
import javafx.stage.Window;

public class Options {


    public static Alert showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
        return alert;
    }

    public static Double calculateLiquidity
            (double bankroll,
             double shortFinInvestments,
             double shortReceivables,
             double shortLiabilities)
    {
        return (bankroll+shortFinInvestments+shortReceivables)
                /shortLiabilities;
    }

    public static Double calculateSolvency
            (double intangibleAssets,
             double mainAssets,
             double prodReserves,
             double unfinishedProduction,
             double finishedProducts,
             double borrowedFunds)
    {
        return (intangibleAssets+mainAssets+prodReserves+unfinishedProduction+finishedProducts)
                /borrowedFunds;
    }
}
