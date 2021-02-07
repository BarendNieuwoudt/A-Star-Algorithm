from BasicCoordinate import BasicCoordinate as bc

class AStarAlgorithmSearch:

	def __init__(self, startCoord, endCoord, obstacleCoords, minX, maxX, minY, maxY):
		self.start = startCoord
		self.end = endCoord
		self.obstacles = obstacleCoords
		self.minX = minX
		self.maxX = maxX
		self.minY = minY
		self.maxY = maxY
		
	def search(self, diagonalTravel = False):
		# The frontier contains all the locations that still have to be searched. 
		frontier = []
		frontier.append(self.start)
		
		visited = []
		visited.append(self.start)
		
		# Save the path traveled.
		cameFrom = {}
		cameFrom[self.start] = None
		
		while not len(frontier) == 0:
			# while the frontier is not empty, there are still locations to consider.
			# retrive the loction to be considered
			currentLocation = frontier.pop(0)
			
			if currentLocation.equals(self.end):
				print(str(self.describePath(cameFrom, self.end)))
				# End location found, stop immediately
				break
			
			# find all the neighbours for the current location
			neighbours = self.getNeighbours(currentLocation, diagonalTravel)
			
			for neighbour in neighbours:
				if not self.listContainsCoord(visited, neighbour):
					# print('considering ' + str(neighbour.getX()) + ' ' + str(neighbour.getY()))
					frontier.append(neighbour)
					visited.append(neighbour)
					cameFrom[neighbour] = currentLocation
					
	# method to return any open locations, that is to say. Coordinations that are not
	# obstacles, and are inside the min and max x and y limitations.
	def getNeighbours(self, coord, diagonalTravel = False):
		possibleNeighbours = []
		neighbours = []
		
		# each location will have a maximum of 8 neighbours.
		possibleNeighbours.append(bc(coord.getX(), coord.getY() - 1))
		possibleNeighbours.append(bc(coord.getX() - 1, coord.getY()))
		possibleNeighbours.append(bc(coord.getX() + 1, coord.getY()))
		possibleNeighbours.append(bc(coord.getX(), coord.getY() + 1))
		
		# if diagonal travel is not allowed, disregard these neighbours
		if diagonalTravel:
			possibleNeighbours.append(bc(coord.getX() - 1, coord.getY() - 1))
			possibleNeighbours.append(bc(coord.getX() + 1, coord.getY() - 1))
			possibleNeighbours.append(bc(coord.getX() - 1, coord.getY() + 1))
			possibleNeighbours.append(bc(coord.getX() + 1, coord.getY() + 1))

		for possibleNeighbour in possibleNeighbours:
			if self.isInsideLimits(possibleNeighbour) and not self.listContainsCoord(self.obstacles, possibleNeighbour):
				# if the neighbour being considered is both inside the limits, and not an obstacle
				# this is a neighbour worth searching
				neighbours.append(possibleNeighbour)
				
		# return all the neighbours that should be considered
		return neighbours
	
	# Returns true if a given coordinate is within the boundaries of max and min x and y
	# Returns false otherwise
	def isInsideLimits(self, coord):
		if coord.getX() in range(self.minX, self.maxX + 1) and coord.getY() in range(self.minY, self.maxY + 1):
			return True
		return False
	
	# Returns true if any coordinate in the provided list is equal to the provided coordinate
	def listContainsCoord(self, listOfCoords, coord):
		for coordinate in listOfCoords:
			if coord.equals(coordinate):
				return True
		return False
	
	# Recursively describes the path from the end coordinate to the start coordinate
	def describePath(self, path, coord):
		for key in path:
			if key.equals(coord):
				if path[key] == None:
					return str(key)
				else:
					return str(self.describePath(path, path[key])) + ' to ' + str(key)
		
			
	