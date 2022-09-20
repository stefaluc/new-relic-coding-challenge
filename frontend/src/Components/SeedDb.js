import * as React from "react";

import * as apiRoutes from './util/api-routes';

export default function SeedDb() {
  let [numCustomers, setNumCustomers] = React.useState(null);
  let [res, setRes] = React.useState(null);

  async function seedDb(numCustomers) {
    let response = await fetch(apiRoutes.seedDb(numCustomers)).then(res => {
      if (res.ok) {
        setRes(`Successfully seeded database with ${numCustomers} new customers`);
      } else {
        setRes(`Error seeding database`);
      }
    }).catch(error => {
      setRes(`Error seeding database`);
    });
    return response;
  }

  function handleSubmit(event) {
    event.preventDefault();
    let formData = new FormData(event.currentTarget);
    let numCustomers = formData.get("num");
    if (!numCustomers) return;
    setNumCustomers(numCustomers);
    seedDb(numCustomers);
  }

  return (
    <div>
      <strong>Seed database with new customers</strong>
      <div style={{ display: "flex" }}>
        <form onSubmit={handleSubmit}>
          <label>
            <input defaultValue={numCustomers ?? undefined} type="text" name="num" placeholder="Number of customers" />
          </label>
          <button type="submit">Seed Database</button>
        </form>
      </div>
      {res && (<div>{res}</div>)}
    </div>
  );
}
