import * as React from "react";
import { Link } from "react-router-dom";

export default function Home() {
  return (
    <>
      <div>
        <h3>
          <Link to="/scenarioa">Scenario A (Search)</Link>
        </h3>
      </div>
      <div>
        <h3>
          <Link to="/scenarioc">Scenario C (Sort)</Link>
        </h3>
      </div>
    </>
  );
}
