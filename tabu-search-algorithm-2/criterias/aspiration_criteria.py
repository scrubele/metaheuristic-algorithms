from strategies.optimization_strategies import OptimizationStrategy


class AspirationCriteria:

    def __init__(self, criteria=OptimizationStrategy.MINIMIZATION):
        self.criteria = criteria

    def is_satisfied(self, current_value, best_value) -> bool:
        return self.criteria.is_optimized(current_value=current_value, best_value=best_value)
