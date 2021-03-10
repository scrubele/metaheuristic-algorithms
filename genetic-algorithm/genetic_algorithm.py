import numpy as np
import matplotlib.pyplot as plt

from population import TSPPopulation, OneMaxPopulation
from utilities import plot_solutions, read_from_resources

np.random.seed(42)


class GeneticAlgorithm:
    def __init__(
        self,
        adjacency_mat,
        is_cities=False,
        population_class=TSPPopulation,
        n_population=5,
        selectivity=0.15,
        crossover_probability=0.5,
        mutation_probability=0.1,
    ):
        self.adjacency_mat = adjacency_mat
        self.cities = [i for i in range(len(adjacency_mat))] if is_cities else None
        self.n_population = n_population
        self.selectivity = selectivity
        self.crossover_probability = crossover_probability
        self.mutation_probability = mutation_probability
        self.population_class = population_class

    def init_population(self, adjacency_mat, n_population, cities=None):
        return self.population_class.get_random_population(adjacency_mat=adjacency_mat, n_population=n_population, cities=cities)

    def run(
        self,
        n_iter=20,
        print_interval=100,
        return_history=False,
        verbose=False,
    ):
        pop = self.init_population(cities=self.cities, adjacency_mat=self.adjacency_mat, n_population=self.n_population)
        best = pop.best
        score = float("inf")
        history = []
        for i in range(n_iter):
            pop.select(self.n_population * self.selectivity)
            history.append(pop.score)
            if verbose:
                print(f"Generation {i}: {pop.score}")
            elif i % self.print_interval == 0:
                print(f"Generation {i}: {pop.score}")
            if pop.score < score:
                best = pop.best
                score = pop.score
            children = pop.mutate(self.crossover_probability, self.mutation_probability)
            pop = self.population_class(children, pop.adjacency_mat)
        if return_history:
            return best, history
        return best




def tsp_test(run=False):
    # if run:
    resources = ["five.19.tsp", "p01.15.291.tsp", "gr17.2085.tsp", "br17.39.atsp"]
    adjacency_mat = read_from_resources(resources[3])
    genetic_algorithm = GeneticAlgorithm(
        adjacency_mat, population_class=TSPPopulation, is_cities=True
    )
    best, history = genetic_algorithm.run(
        n_iter=4000, return_history=True, verbose=True
    )
    plot_solutions(history)
    print("Best solution:", best)


def one_max_test(run=False):
    adjency_list_size=20
    genetic_algorithm = GeneticAlgorithm(
        adjency_list_size, population_class=OneMaxPopulation
    )
    best, history = genetic_algorithm.run(n_iter=120, return_history=True, verbose=True)
    plot_solutions(history)
    print("Best solution:", best)

if __name__ == "__main__":
    # tsp_test()
    one_max_test(True)
