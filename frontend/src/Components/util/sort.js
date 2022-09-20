export const SORT_LIMIT = 50;

export function createSortOptions() {
  const sortOptions = {}
  SORT_BY_OPTIONS.forEach(([property, propertyName]) => {
    createSortOption(sortOptions, property, propertyName);
  });
  return sortOptions;
}

const SORT_BY_OPTIONS = [
  ['firstName', 'First Name'],
  ['lastName', 'Last Name'],
  ['companyName', 'Company Name'],
];

function createSortOption(sortOptions, property, propertyName) {
  const keyDesc = `${property}Desc`;
  sortOptions[keyDesc] = { sortBy: property, sortDir: 'desc', name: `${propertyName}, Descending` };
  const keyAsc = `${property}Asc`;
  sortOptions[keyAsc] = { sortBy: property, sortDir: 'asc', name: `${propertyName}, Ascending` };
}