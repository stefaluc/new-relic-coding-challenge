import * as React from "react";
import { Link, useSearchParams } from "react-router-dom";

import * as apiRoutes from './util/api-routes';
import { createSortOptions, SORT_LIMIT } from './util/sort';

export default function Sort() {
  const [searchParams, setSearchParams] = useSearchParams();

  const sortBy = searchParams.get("sortBy");
  const sortDir = searchParams.get("sortDir");

  const [customerData, setCustomerData] = React.useState(null);
  const sortOptions = createSortOptions();

  React.useEffect(() => {
    let abortController = new AbortController();

    async function sortCustomers() {
      let response = await fetch(apiRoutes.sortCustomers(sortBy, sortDir));
      if (!abortController.signal.aborted) {
        let data = await response.json();
        setCustomerData(data.slice(0, SORT_LIMIT));
      }
    }
    
    async function getAllCustomers() {
      let response = await fetch(apiRoutes.getCustomers());
      if (!abortController.signal.aborted) {
        let data = await response.json();
        setCustomerData(data.slice(0, SORT_LIMIT));
      }
    }

    if (sortBy && sortDir) {
      sortCustomers();
    } else {
      getAllCustomers();
    }

    return () => {
      abortController.abort();
    };
  }, [sortBy, sortDir]);

  function handleSubmit(event) {
    event.preventDefault();
    const formData = new FormData(event.currentTarget);
    const sortOption = formData.get('sortOption');
    const { sortBy, sortDir } = sortOptions[sortOption];
    if (!sortOption) return;
    setSearchParams({ sortBy, sortDir });
  }

  return (
    <div>
      <h4><Link to="/">Back to main page</Link></h4>
      <h4>Sort Customers:</h4>
      <div style={{ display: "flex" }}>
        <form onSubmit={handleSubmit}>
          <select id="sort" name="sortOption">
            {Object.entries(sortOptions).map(([key, sort]) => (
              <option key={key} value={key}>{sort.name}</option>
            ))}
          </select>
          <button type="submit">Sort</button>
        </form>
      </div>
      <br />
      {customerData && (<div>Showing maximum <strong>{customerData.length}</strong> customers:</div>)}
      {customerData && (customerData.map(customer => (
        <div
          style={{
            padding: "24px",
            margin: "24px 0",
            borderTop: "1px solid #eaeaea",
            display: "flex",
            alignItems: "center",
            gap: "16px",
          }}
        >
          <div>
            <h2>{customer.fullName}</h2>
            <p>{customer.companyName}</p>
          </div>
        </div>
      )))}
    </div>
  );
}
