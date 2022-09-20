import * as React from 'react';
import { Link, useSearchParams } from 'react-router-dom';

import * as apiRoutes from './util/api-routes';

export default function Search() {
  let [searchParams, setSearchParams] = useSearchParams();

  let query = searchParams.get("search");

  let [searchData, setSearchData] = React.useState(null);

  React.useEffect(() => {
    let abortController = new AbortController();

    async function searchCustomer() {
      let response = await fetch(apiRoutes.searchCustomers(query), {
        signal: abortController.signal,
      });
      if (!abortController.signal.aborted) {
        let data = await response.json();
        setSearchData(data);
      }
    }

    if (query) {
      searchCustomer();
    }

    return () => {
      abortController.abort();
    };
  }, [query]);

  function handleSubmit(event) {
    event.preventDefault();
    let formData = new FormData(event.currentTarget);
    let newSearch = formData.get("query");
    if (!newSearch) return;
    setSearchParams({ search: newSearch });
  }

  return (
    <div>
      <h4><Link to="/">Back to main page</Link></h4>
      <h4>Search by customer name:</h4>
      <div style={{ display: "flex" }}>
        <form onSubmit={handleSubmit}>
          <label>
            <input defaultValue={query ?? undefined} type="text" name="query" />
          </label>
          <button type="submit">Search</button>
        </form>
      </div>

      {searchData && (<div>{searchData.length} customers found for search result <strong>"{query}"</strong></div>)}
      {searchData && (searchData.map(customer => (
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
