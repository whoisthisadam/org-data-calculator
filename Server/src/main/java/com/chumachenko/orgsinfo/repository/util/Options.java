package com.chumachenko.orgsinfo.repository.util;


public class Options {

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
