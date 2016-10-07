package BlackJack.model.rules;

public class RulesFactory {

    public IHitStrategy GetHitRule() {
        return new SoftSeventeenHitStrategy();
    }

    public INewGameStrategy GetNewGameRule() {
        return new AmericanNewGameStrategy();
    }

    public IWinStrategy GetWinRule() {
        return new AdvantagePlayerWinStrategy();
    }
}