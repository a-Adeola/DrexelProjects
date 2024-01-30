import random
import sys
import time
import math as Math


class BrickPuzzle:

    def __init__(self):
        self.width = 0
        self.height = 0
        self.grid = [[]]
        self.state = None
        self.finalNode = None

    def loadGame(self, textfile):
        try:
            f = open(textfile, "r")
            line1 = f.readline()
            lines = f.readlines()

            f.close()

        except:
            print("File failed to open. Try again \n")

        self.setWidth(line1[0])
        self.setHeight(line1[2])
        self.createGrid(lines)

    def setWidth(self, widthValue):
        self.width = int(widthValue)

    def setHeight(self, heightValue):
        self.height = int(heightValue)

    def getState(self):
        return self.state

    def cloneState(self, givenState):
        cloned = [[0] * self.width for n in range(self.height)]

        for row in range(self.height):
            for column in range(self.width):
                cloned[row][column] = givenState[row][column]

        return cloned

    def compareStates(self, A, B):
        return (A == B)

    def solved(self, gameState):

        return not any(-1 in sl for sl in gameState)

    def displayState(self, givenState):
        print(self.width, end=",")
        print(self.height, end=",")
        print()
        for row in range(self.height):
            for column in range(self.width):
                if (givenState[row][column] >= 0 and givenState[row][column] < 10):
                    print(" {}".format(givenState[row][column]), end=",")
                else:
                    print(givenState[row][column], end=",")
            print("", end="\n")

    def createGrid(self, L):
        self.state = [[0] * self.width for n in range(self.height)]

        myList = []
        column = 0
        row = 0
        cnt = 0
        multiplier = 1
        cpy = ''

        # to remove the commas
        for line in L:
            for e in line:
                try:
                    if e == '-':
                        multiplier = -1
                    if e == ',':
                        myList.append(int(cpy) * multiplier)
                        multiplier = 1
                        cpy = ''
                    if e != '-' and e != ',':
                        cpy = cpy + e

                except:
                    continue

        # inserting correct values to matrix/nested list
        for i in range(len(myList)):
            if cnt < self.width:

                self.state[row][column] = myList[i]
                column += 1
                cnt += 1
            else:
                column = 0
                row += 1
                self.state[row][column] = myList[i]
                cnt = +1
                column += 1

    def availableMoves(self, givenState):
        allMoves = []
        finalMoves = []
        pieceCounts = {}
        piecePlacements = {}

        # dictionary for count
        for row in givenState:
            for e in row:
                if e > 1:
                    if e in pieceCounts:
                        x = pieceCounts.get(e)
                        x += 1
                        pieceCounts.update({e: x})
                    else:
                        pieceCounts[e] = 1

        # dictionary for piece placements
        currentList = []
        for row in range(len(givenState)):
            for column in range(len(givenState[0])):
                if givenState[row][column] > 1:
                    if givenState[row][column] in piecePlacements:
                        currentList = piecePlacements.get(givenState[row][column])
                        currentList.append([row, column])
                        piecePlacements.update({givenState[row][column]: currentList})
                    else:
                        piecePlacements[givenState[row][column]] = [[row, column]]

        for p in piecePlacements:
            places = piecePlacements.get(p)
            cnt = len(places)

            # checks if all blocks of a piece can move in a certian direction
            for r in range(cnt):
                point = places[r]
                if p != 2:
                    if givenState[point[0]][point[1] + 1] == p or givenState[point[0]][point[1] + 1] == 0:
                        allMoves.append((p, "Right"))
                    if givenState[point[0]][point[1] - 1] == p or givenState[point[0]][point[1] - 1] == 0:
                        allMoves.append((p, "Left"))
                    if givenState[point[0] - 1][point[1]] == p or givenState[point[0] - 1][point[1]] == 0:
                        allMoves.append((p, "Up"))
                    if givenState[point[0] + 1][point[1]] == p or givenState[point[0] + 1][point[1]] == 0:
                        allMoves.append((p, "Down"))
                else:
                    if givenState[point[0]][point[1] + 1] == p or givenState[point[0]][point[1] + 1] == 0 or \
                            givenState[point[0]][point[1] + 1] == -1:
                        allMoves.append((p, "Right"))
                    if givenState[point[0]][point[1] - 1] == p or givenState[point[0]][point[1] - 1] == 0 or \
                            givenState[point[0]][point[1] - 1] == -1:
                        allMoves.append((p, "Left"))
                    if givenState[point[0] - 1][point[1]] == p or givenState[point[0] - 1][point[1]] == 0 or \
                            givenState[point[0] - 1][point[1]] == -1:
                        allMoves.append((p, "Up"))
                    if givenState[point[0] + 1][point[1]] == p or givenState[point[0] + 1][point[1]] == 0 or \
                            givenState[point[0] + 1][point[1]] == -1:
                        allMoves.append((p, "Down"))

        for move in allMoves:
            moveCount = allMoves.count(move)
            if move[0] in pieceCounts:
                if moveCount == pieceCounts.get(move[0]):
                    finalMoves.append(move)

        finalMoves = list(set(finalMoves))

        return finalMoves

    def performMove(self, givenState, move):
        piece = move[0]
        movement = move[1]
        allPieces = []
        newIndex = []

        #stateClone = self.cloneState(givenState)

        # get the index of all locations of the piece to move
        for row in range(len(givenState)):
            for column in range(len(givenState[0])):
                if givenState[row][column] == piece:
                    allPieces.append([row, column])

        for index in allPieces:
            if movement == 'Down':
                newIndex.append([index[0] + 1, index[1]])
                givenState[index[0]][index[1]] = 0

            if movement == 'Up':
                newIndex.append([index[0] - 1, index[1]])
                givenState[index[0]][index[1]] = 0

            if movement == 'Right':
                newIndex.append([index[0], index[1] + 1])
                givenState[index[0]][index[1]] = 0

            if movement == 'Left':
                newIndex.append([index[0], index[1] - 1])
                givenState[index[0]][index[1]] = 0

        for newI in newIndex:
            givenState[newI[0]][newI[1]] = piece

        return givenState

    def normalize(self, givenBoard):
        nextIdx = 3
        for row in range(len(givenBoard)):
            for col in range(len(givenBoard[0])):
                if givenBoard[row][col] == nextIdx:
                    nextIdx += 1
                else:
                    if givenBoard[row][col] > nextIdx:
                        self.swapIdx(givenBoard, nextIdx, givenBoard[row][col])
                        nextIdx += 1
        return givenBoard

    def swapIdx(self, givenState, idx1, idx2):
        for row in range(len(givenState)):
            for col in range(len(givenState[0])):
                if givenState[row][col] == idx1:
                    givenState[row][col] = idx2
                else:
                    if givenState[row][col] == idx2:
                        givenState[row][col] = idx1

    def randomWalks(self, givenState, number):
        cnt = 0
        isSolved = False
        self.displayState(givenState)

        while isSolved == False and (cnt < int(number)):
            allMoves = self.availableMoves(givenState)
            choice = random.choice(allMoves)
            print(choice)
            self.performMove(givenState, choice)
            self.normalize(givenState)
            self.displayState(givenState)
            cnt += 1
            isSolved = self.solved(givenState)


    def dfs(self, givenState):
        visiting = []
        visited = []
        explored = False
        current = Node()
        solution = None
        totalMoves = []

        # last in first out
        isGoal = self.solved(givenState)
        if isGoal:
            return True

        givenState = self.normalize(givenState)
        current.setState(givenState)
        visiting.append(current)

        while not isGoal:
            if not visiting:
                print("no solution found")
                return None

            current = visiting.pop()
            visited.append(current)

            moves = self.availableMoves(current.getState())

            for move in moves:
                child = Node()
                clone = self.cloneState(current.getState())

                child.setState(self.normalize(self.performMove(clone, move)))
                child.setMove(move)
                child.setParent(current)

                for n in visiting:
                    if self.compareStates(n.getState(), child.getState()):
                        explored = True
                        break

                if not explored:
                    for c in visited:
                        if self.compareStates(c.getState(), child.getState()):
                            explored = True

                if not explored:
                    if self.solved(child.getState()):
                        solution = child
                        visited.append(child)
                        isGoal = True
                    else:
                        visiting.append(child)
                explored = False

        returnSol = solution
        while solution is not None:
            totalMoves.append(solution.getMove())
            solution = solution.getParent()

        totalMoves.remove(None)

        return totalMoves, returnSol.getState(), len(visited)


    def bfs(self, givenState):
        visiting = []
        visited = []
        explored = False
        current = Node()
        solution = None
        totalMoves = []

        #first in first out
        isGoal = self.solved(givenState)
        if isGoal:
            print("goal")
            return True

        givenState = self.normalize(givenState)
        current.setState(givenState)
        visiting.append(current)

        while not isGoal:
            if not visiting:
                print("no solution found")
                break

            current = visiting.pop(0)
            visited.append(current)

            moves = self.availableMoves(current.getState())

            for move in moves:
                child = Node()
                clone = self.cloneState(current.getState())

                child.setState(self.normalize(self.performMove(clone, move)))
                child.setMove(move)
                child.setParent(current)

                for n in visiting:
                    if self.compareStates(n.getState(), child.getState()):
                        explored = True
                        break

                if not explored:
                    for c in visited:
                        if self.compareStates(c.getState(), child.getState()):
                            explored = True

                if not explored:
                    if self.solved(child.getState()):
                        solution = child
                        visited.append(child)
                        isGoal = True
                    else:
                        visiting.append(child)
                explored = False

        returnSol = solution
        while solution is not None:
            totalMoves.append(solution.getMove())
            solution = solution.getParent()

        totalMoves.remove(None)

        return totalMoves, returnSol.getState(), len(visited)

    def ids(self, givenState):
        counter = 1
        visiting = []
        current = Node()
        totalMoves = []

        isGoal = self.solved(givenState)
        if isGoal:
            return True

        givenState = self.normalize(givenState)
        current.setState(givenState)
        current.setLevel(0)
        visiting.append(current)

        while not isGoal:
            current = visiting.pop(0)

            moves = self.availableMoves(current.getState())

            for move in moves:
                child = Node()
                clone = self.cloneState(current.getState())

                child.setState(self.normalize(self.performMove(clone, move)))
                child.setMove(move)
                child.setParent(current)
                child.setLevel((child.getParent().getLevel()) + 1)
                visiting.append(child)

            for v in visiting:
                if v.getLevel() <= counter:
                    sol = self.idshelper(v)
                    if sol is False:
                        continue
                    else:
                        isGoal = True
                        break
                else:
                    break

            counter += 1

        solution = self.finalNode

        while solution is not None:
            totalMoves.append(solution.getMove())
            solution = solution.getParent()

        totalMoves.remove(None)

        return totalMoves, self.finalNode.getState(), self.finalNode.getLevel()

    def idshelper(self, givenNode):
        visiting = []
        visited = []
        explored = False
        current = givenNode

        isGoal = self.solved(current.getState())
        if isGoal:
            self.finalNode = givenNode
            print("goal")
            return True

        givenState = self.normalize(current.getState())
        current.setState(givenState)
        visiting.append(current)

        while not isGoal:
            if not visiting:
                return False

            current = visiting.pop()
            visited.append(current)

            moves = self.availableMoves(current.getState())

            for move in moves:
                child = Node()
                clone = self.cloneState(current.getState())

                child.setState(self.normalize(self.performMove(clone, move)))
                child.setMove(move)
                child.setParent(current)

                for n in visiting:
                    if self.compareStates(n.getState(), child.getState()):
                        explored = True
                        break

                if not explored:
                    for c in visited:
                        if self.compareStates(c.getState(), child.getState()):
                            explored = True

                if not explored:
                    if self.solved(child.getState()):
                        child.setLevel(len(visited)+1)
                        self.finalNode = child

                        isGoal = True
                        return isGoal
                    else:
                        visiting.append(child)
                explored = False

        return False

    def astar(self, givenState):
        visiting = []
        visited = []
        explored = False
        current = Node()
        totalMoves = []

        isGoal = self.solved(givenState)
        if isGoal:
            print("goal")
            return True

        givenState = self.normalize(givenState)
        current.setState(givenState)
        visiting.append(current)

        while not isGoal:
            if not visiting:
                print("no solution found")
                break

            current = visiting[0]

            index = 0
            for i in range(len(visiting)):
                if visiting[i].getTotalCost() < current.getTotalCost():

                    index = i

            current = visiting.pop(index)
            visited.append(current)

            isGoal = self.solved(current.getState())
            if isGoal:
                self.finalNode = current
                break

            moves = self.availableMoves(current.getState())

            for move in moves:
                child = Node()
                clone = self.cloneState(current.getState())

                child.setState(self.normalize(self.performMove(clone, move)))
                child.setMove(move)
                child.setParent(current)
                child.setCost(current.getPathCost() + 1)

                if not explored:
                    for c in visited:
                        if self.compareStates(c.getState(), child.getState()):
                            explored = True
                            break

                for n in visiting:
                    if self.compareStates(n.getState(), child.getState()):
                        if child.getPathCost() < n.getPathCost():
                            visiting.remove(n)
                            self.computeMHC(child)
                            visiting.append(child)

                        explored = True
                        break

                if not explored:

                    self.computeMHC(child)
                    visiting.append(child)

                explored = False

        solution = self.finalNode

        while solution is not None:
            totalMoves.append(solution.getMove())
            solution = solution.getParent()

        totalMoves.remove(None)

        return totalMoves, self.finalNode.getState(), len(visited)


    def manhattanHeuristic(self, givenNode):

        givenState = givenNode.getState()
        piecePlacements = {}

        for row in range(len(givenState)):
            for column in range(len(givenState[0])):
                if givenState[row][column] == -1:
                    if givenState[row][column] not in piecePlacements:
                        piecePlacements[givenState[row][column]] = [[row, column]]

                if givenState[row][column] == 2:
                    if givenState[row][column] not in piecePlacements:
                        piecePlacements[givenState[row][column]] = [[row, column]]

        if -1 not in piecePlacements:
            return 0

        x = piecePlacements.get(2)
        x1 = x[0][0]
        y1 = x[0][1]

        y = piecePlacements.get(-1)
        x2 = y[0][0]
        y2 = y[0][1]

        distance = int(Math.fabs(x2 - x1) + Math.fabs(y2 - y1))

        return distance

    def computeMHC(self, givenNode):
        givenNode.setHeuristic(self.manhattanHeuristic(givenNode))
        givenNode.setTotalCost(givenNode.getPathCost() + givenNode.getHeuristic())
        return givenNode


class Node:
    def __init__(self):
        self.parent = None
        self.state = None
        self.move = None
        self.heuristicCost = 0
        self.pathCost = 0
        self.totCost = 0
        self.level = 0

    def getParent(self):
        return self.parent

    def getState(self):
        return self.state

    def getMove(self):
        return self.move

    def setMove(self, m):
        self.move = m

    def setState(self, givenState):
        self.state = givenState

    def setParent(self, p):
        self.parent = p

    def setLevel(self, l):
      self.level = l

    def getLevel(self):
      return self.level

    def setHeuristic(self, h):
        self.heuristicCost = h

    def setCost(self,c):
        self.pathCost = c

    def setTotalCost(self,t):
        self.totCost = t

    def getPathCost(self):
        return self.pathCost

    def getTotalCost(self):
        return self.totCost

    def getHeuristic(self):
        return self.heuristicCost

if __name__ == "__main__":
    startTime = time.time()
    sbp = BrickPuzzle()

    request = sys.argv[1]

    if request == "print":
        sbp.loadGame(sys.argv[2])
        current = sbp.getState()
        sbp.displayState(current)

    elif request == "done":
        sbp.loadGame(sys.argv[2])
        current = sbp.getState()
        print(sbp.solved(current))

    elif request == "availableMoves":
        sbp.loadGame(sys.argv[2])
        current = sbp.getState()
        moves = sbp.availableMoves(current)
        for m in moves:
            print(m)

    elif request == "applyMove":
        sbp.loadGame(sys.argv[2])
        current = sbp.getState()
        current = sbp.performMove(current, sys.argv[3])
        sbp.displayState(current)

    elif request == "compare":
        stateA = BrickPuzzle()
        stateB = BrickPuzzle()

        stateA.loadGame(sys.argv[2])
        stateB.loadGame(sys.argv[3])

        print(stateA.compareStates(stateA.getState(), stateB.getState()))

    elif request == "norm":
        sbp.loadGame(sys.argv[2])
        current = sbp.getState()
        sbp.normalize(current)
        sbp.displayState(current)

    elif request == "random":
        sbp.loadGame(sys.argv[2])
        current = sbp.getState()
        sbp.randomWalks(current, sys.argv[3])

    elif request == "bfs":
        sbp.loadGame(sys.argv[2])
        current = sbp.getState()
        moves, state, visited = sbp.bfs(current)
        endTime = time.time()
        moves.reverse()
        for m in moves:
            print(m)

        print()
        sbp.displayState(state)
        print()
        print(visited)
        print((endTime - startTime) * 1000)
        print(len(moves))

    elif request == "dfs":
        sbp.loadGame(sys.argv[2])
        current = sbp.getState()
        moves, state, visited = sbp.dfs(current)
        endTime = time.time()
        moves.reverse()
        for m in moves:
            print(m)

        print()
        sbp.displayState(state)
        print()
        print(visited)
        print((endTime - startTime) * 1000)
        print(len(moves))

    elif request == "ids":
        sbp.loadGame(sys.argv[2])
        current = sbp.getState()
        moves, state, visited = sbp.ids(current)

        endTime = time.time()
        moves.reverse()
        for m in moves:
            print(m)

        print()
        sbp.displayState(state)
        print()
        print(visited)
        print((endTime - startTime) * 1000)
        print(len(moves))

    elif request == "astar":
        sbp.loadGame(sys.argv[2])
        current = sbp.getState()
        moves, state, visited = sbp.astar(current)

        endTime = time.time()
        moves.reverse()
        for m in moves:
            print(m)

        print()
        sbp.displayState(state)
        print()
        print(visited)
        print((endTime - startTime) * 1000)
        print(len(moves))
