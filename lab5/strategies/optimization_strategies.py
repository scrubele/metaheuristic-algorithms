from enum import Enum, auto


class OptimizationStrategy(Enum):
    MINIMIZATION = auto()
    MAXIMIZATION = auto()

    def is_optimized(self, current_value, best_value) -> bool:
        if self.value == self.MINIMIZATION.value:
            return current_value <= best_value
        elif self.value == self.MAXIMIZATION.value:
            return current_value >= best_value
        return False
