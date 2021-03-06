from copy import deepcopy

from criterias.aspiration_criteria import AspirationCriteria
from strategies.neighbourhood_strategies import NeighbourhoodGenerationStrategy


class SolutionNeighbourLocator:

    def __init__(self, initial_solution, intensification, diversification, aspiration_criteria=AspirationCriteria,
                 neighbourhood_criteria=NeighbourhoodGenerationStrategy.TWO_OPT):
        self.initial_solution = initial_solution
        self.middle_term_memory = intensification
        self.long_term_memory = diversification
        self.aspiration_criteria = aspiration_criteria
        self.neighbourhood_criteria = neighbourhood_criteria
        self.normalize_frozen_values()
        self.pick_values()
        self.candidate_solutions = self.neighbourhood_criteria.generate_neighbours(self.initial_solution,
                                                                                   frozen_values=self.frozen_values,
                                                                                   long_memory=diversification,
                                                                                   should_pick_values=self.should_pick_values)

    def normalize_frozen_values(self):
        frozen_values = self.middle_term_memory.frozen_values
        self.frozen_values = [(key, value[0]) for key, value in frozen_values.items()]
        # print("Frozen values:", self.frozen_values)

    def pick_values(self):
        should_pick_values = self.long_term_memory.should_pick_values
        self.should_pick_values = [(key, value[0]) for key, value in should_pick_values.items()]
        self.long_term_memory.should_pick_values = {}
        # print("Frozen values:", self.frozen_values)

    def find_best_neighbour(self, tabu_list):
        candidate_solution_list = deepcopy(self.candidate_solutions)
        if len(candidate_solution_list) > 0:
            best_admissible_solution = min(candidate_solution_list)
            return best_admissible_solution
        return None
