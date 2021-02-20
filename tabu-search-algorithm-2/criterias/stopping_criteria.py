MAX_ITERATIONS = 20


class IterationStopCriteria:
    max_iteration_number: int

    def __init__(self, max_iterations=MAX_ITERATIONS):
        self.max_iteration_number = max_iterations

    def is_satisfied(self, current_iteration) -> bool:
        return current_iteration <= self.max_iteration_number
