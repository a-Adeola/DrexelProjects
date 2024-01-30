import math
import random

import game


class HumanPlayer(game.Player):

    def __init__(self):
        super().__init__()

    def choose_move(self, state):
        # generate the list of moves:
        moves = state.generateMoves()

        for i, action in enumerate(moves):
            print('{}: {}'.format(i, action))
        response = input('Please choose a move: ')
        return moves[int(response)]


class RandomAgent(game.Player):
    def __init__(self):
        super().__init__()

    def choose_move(self, givenState):
        moves = givenState.generateMoves()

        if not moves:
            return None

        move = random.choice(moves)
        return move


class MinimaxAgent(game.Player):
    def __init__(self, d_limit):
        self.limit = d_limit

        if d_limit < 1:
            exit()

    def choose_move(self, givenState):
        moves = givenState.generateMoves()

        if not moves:
            return None

        if givenState.nextPlayerToMove == 0:
            return self.maximize(givenState, moves)
        else:
            return self.minimize(givenState, moves)

    def minScore(self, givenState, depth):
        minimum = math.inf
        if depth >= self.limit:
            return givenState.score()

        moves = givenState.generateMoves()

        if not moves:
            return givenState.score()

        for move in moves:
            temp = givenState.applyMoveCloning(move)
            maximum = self.maxScore(temp, depth + 1)
            minimum = min(minimum, maximum)

        return minimum

    def maxScore(self, givenState, depth):
        maximum = -math.inf
        if depth >= self.limit:
            return givenState.score()

        moves = givenState.generateMoves()

        if not moves:
            return givenState.score()

        for move in moves:
            temp = givenState.applyMoveCloning(move)
            minimum = self.minScore(temp, depth + 1)
            maximum = max(minimum, maximum)

        return maximum

    def maximize(self, givenState, moves):
        maximum = -math.inf
        maxMove = None

        for move in moves:
            temp = givenState.applyMoveCloning(move)
            score = self.minScore(temp, 0)
            if score > maximum:
                maximum = score
                maxMove = move

        return maxMove

    def minimize(self, givenState, moves):
        minimum = math.inf
        minimumMove = None

        for move in moves:
            temp = givenState.applyMoveCloning(move)
            score = self.maxScore(temp, 0)
            if score < minimum:
                minimum = score
                minimumMove = move

        return minimumMove

class AlphaBeta(game.Player):
    def __init__(self, d_limit):
        self.limit = d_limit

        if d_limit < 1:
            exit()

    def choose_move(self, givenState):
        alpha = -math.inf
        beta = math.inf
        moves = givenState.generateMoves()
        maximum = -math.inf
        minimum = math.inf

        if not moves:
            return None
        bestMove = None

        if givenState.nextPlayerToMove == 0:
            for move in moves:
                temp = givenState.applyMoveCloning(move)
                score = self.minValue(temp, 0, alpha, beta)
                if score > maximum:
                    maximum = score
                    bestMove = move

            return bestMove
        else:
            for move in moves:
                temp = givenState.applyMoveCloning(move)
                score = self.maxValue(temp, 0, alpha, beta)
                if score < minimum:
                    minimum = score
                    bestMove = move

            return bestMove

    def minValue(self, givenState, depth, alpha, beta):
        minimum = math.inf
        if depth >= self.limit:
            return givenState.score()

        moves = givenState.generateMoves()

        if not moves:
            return givenState.score()

        for move in moves:
            temp = givenState.applyMoveCloning(move)
            maximum = self.maxValue(temp, depth + 1, alpha, beta)
            minimum = min(minimum, maximum)

            if alpha >= minimum:
                return minimum

            beta = min(beta, minimum)

        return minimum

    def maxValue(self, givenState, depth, alpha, beta):
        maximum = -math.inf

        if depth >= self.limit:
            return givenState.score()

        moves = givenState.generateMoves()

        if not moves:
            return givenState.score()

        for move in moves:
            temp = givenState.applyMoveCloning(move)
            minimum = self.minValue(temp, depth + 1, alpha, beta)
            maximum = max(minimum, maximum)
            if maximum >= beta:
                return maximum
            alpha = max(alpha, maximum)

        return maximum
