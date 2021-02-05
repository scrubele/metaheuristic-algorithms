from enum import Enum, auto


class Optimization(Enum):
    MINIMIZATION = auto()
    MAXIMIZATION = auto()

    def is_optimized(self, current_value, best_value) -> bool:
        if self.value == self.MINIMIZATION.value:
            return current_value <= best_value
        elif self.value == self.MAXIMIZATION.value:
            return current_value >= best_value
        return False


class AspirationCriteria:

    def __init__(self, criteria=Optimization.MINIMIZATION):
        self.criteria = criteria

    def is_satisfied(self, current_value, best_value) -> bool:
        return self.criteria.is_optimized(current_value=current_value, best_value=best_value)
