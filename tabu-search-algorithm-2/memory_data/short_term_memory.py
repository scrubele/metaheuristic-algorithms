from filecmp import cmp
from queue import Queue

from strategies.memory_strategies import MemoryStrategy


class TabuElement:

    def __init__(self, element, value):
        self.element = element
        self.value = value

    def __lt__(self, other):
        return self.value < other.unsuccess_iterations

    def __repr__(self):
        return "Element: " + str(self.element) + " | value: " + str(self.value)

    def __eq__(self, other):
        if not isinstance(other, TabuElement):
            # don't attempt to compare against unrelated types
            return NotImplemented
        return self.value == other.value

    def __key(self):
        return self.value

    def __hash__(self):
        return hash(self.__key())

    def __cmp__(self, other):
        return cmp(self.value, other.value)


class TabuQueue(Queue):

    def __init__(self, maxsize=5, memory_strategy=MemoryStrategy.SAVE_IDS):
        super(TabuQueue, self).__init__(maxsize=maxsize)
        self.memory_strategy = memory_strategy

    def __add(self, element):
        if self.qsize() < self.maxsize:
            self.put(element)
        else:
            self.get()
            self.put(element)

    def __contains__(self, item):
        with self.mutex:
            return item in self.queue

    def put(self, item, block=True, timeout=None) -> object:
        if item not in self.queue:
            Queue.put(self, item, block, timeout)

    def add(self, solution):
        picked_value = self.memory_strategy.pick(solution=solution)
        # print("picked value", picked_value, picked_value not in self.queue)
        tabu_element = TabuElement(picked_value, solution.value)
        if tabu_element not in self.queue:
            self.__add(tabu_element)
