using Microsoft.AspNetCore.Mvc;
using Sportivo_webAPI.Repositories;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace Sportivo_webAPI.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class SportController : Controller
    {
        private readonly SportRepository _sportRepository;
        public SportController()
        {
            _sportRepository = new SportRepository();
        }

        [HttpGet]
        [Route("getAll")]
        public IActionResult Get()
        {
            return Ok(_sportRepository.GetAll());
        }

        [HttpGet]
        [Route("getAllForCompany")]
        public IActionResult GetAllForCompany(int companyId)
        {
            return Ok(_sportRepository.GetAllForCompany(companyId));
        }
    }
}
