MAX_ITERATIONS = 20


class IterationStopCriteria:
    max_iteration_number: int

    def __init__(self, max_iterations=MAX_ITERATIONS):
        self.max_iteration_number = max_iterations

    def is_satisfied(self, current_iteration) -> bool:
        return current_iteration <= self.max_iteration_number

    def is_satisfied(self, current_iteration, previous_solution, best_solution) -> bool:
        is_not_equal = True
        # if previous_solution is not None:
        #     is_not_equal = previous_solution.encoding != best_solution.encoding
        return current_iteration <= self.max_iteration_number and is_not_equal
